package net.vinid.moviedb.data.repository

import android.content.Context
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.realm.Realm
import net.vinid.moviedb.data.NetworkBoundResource
import net.vinid.moviedb.data.NetworkManager
import net.vinid.moviedb.data.Resource
import net.vinid.moviedb.data.local.RealmManager
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.api.APIClient
import net.vinid.moviedb.data.remote.api.APIService
import net.vinid.moviedb.data.remote.respone.ListMovieRespone
import net.vinid.moviedb.util.AppUtils

class MovieRepository(context: Context) {
    private var movieDAO: MovieDAO = RealmManager.createMovieDAO()
    private var apiService: APIService = APIClient.getClient()?.create(APIService::class.java)!!
    private val networkManager: NetworkManager = NetworkManager(context)

    fun loadMovieByCategory(page: Int, category: Int): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, ListMovieRespone>(){
            override fun saveCallResult(item: ListMovieRespone) {
                val realm = Realm.getInstance(AppUtils.initRealmConfig())
                // delete all movie
                movieDAO.deleteMovies(realm)

                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(item.results, category, page)

                for (movie in listMovieEntity){
                    // save movie to db
                    movieDAO.saveMovie(movie,realm)
                }
            }

            override fun shouldFetch(): Boolean {
                // check internet
                return networkManager.isConnectedToInternet
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>>? {
                val listMoviesEntity = movieDAO.getMoviesByPage(page,category)
                Log.d("TEST","check db: "+listMoviesEntity.size)
                if (listMoviesEntity.isNullOrEmpty()){
                    return Flowable.empty()
                }
                return Flowable.fromArray(listMoviesEntity)
            }

            override fun createCall(): Observable<Resource<ListMovieRespone>> {
                when (category) {
                    AppUtils.CATEGORY_POPULAR -> {
                        return apiService.getMoviePopular(page)
                            .flatMap(object :
                                Function<ListMovieRespone, Observable<Resource<ListMovieRespone>>> {
                                override fun apply(t: ListMovieRespone): Observable<Resource<ListMovieRespone>> {
                                    return Observable.just(Resource.success(t))
                                }
                            })
                    }

                    AppUtils.CATEGORY_NOW_PLAYING -> {
                        return apiService.getMovieNowPlaying(page)
                            .flatMap(object :
                                Function<ListMovieRespone, Observable<Resource<ListMovieRespone>>> {
                                override fun apply(t: ListMovieRespone): Observable<Resource<ListMovieRespone>> {
                                    return Observable.just(Resource.success(t))
                                }
                            })
                    }

                    AppUtils.CATEGORY_TOP_RATES -> {
                        return apiService.getMovieTopRated(page)
                            .flatMap(object :
                                Function<ListMovieRespone, Observable<Resource<ListMovieRespone>>> {
                                override fun apply(t: ListMovieRespone): Observable<Resource<ListMovieRespone>> {
                                    return Observable.just(Resource.success(t))
                                }
                            })
                    }

                    AppUtils.CATEGORY_UPCOMING -> {
                        return apiService.getMovieUpcoming(page)
                            .flatMap(object :
                                Function<ListMovieRespone, Observable<Resource<ListMovieRespone>>> {
                                override fun apply(t: ListMovieRespone): Observable<Resource<ListMovieRespone>> {
                                    return Observable.just(Resource.success(t))
                                }
                            })
                    }
                }
                return Observable.empty()
            }

            override fun convertRequestTypeToResultType(requestType: Resource<ListMovieRespone>): Resource<List<MovieEntity>> {
                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(requestType.data?.results!!, category, page)
                return Resource.success(listMovieEntity)
            }
        }.result
    }

}