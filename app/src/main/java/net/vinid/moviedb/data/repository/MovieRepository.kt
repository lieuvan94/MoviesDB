package net.vinid.moviedb.data.repository

import android.content.Context
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.realm.Realm
import net.vinid.moviedb.data.NetworkBoundResource
import net.vinid.moviedb.data.Resource
import net.vinid.moviedb.data.local.RealmManager
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.api.APIClient
import net.vinid.moviedb.data.remote.api.APIService
import net.vinid.moviedb.data.remote.respone.ListMovieRespone
import net.vinid.moviedb.util.ext.AppUtils

class MovieRepository(

) {
    private val MovieReposTag = "MovieRepository"
    private var movieDAO: MovieDAO
    private var apiService: APIService

    init {
        apiService = APIClient.getClient()?.create(APIService::class.java)!!
        movieDAO = RealmManager.createMovieDAO()
    }

    fun loadMovieByCategory(page: Int, category: Int): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, ListMovieRespone>(){
            override fun saveCallResult(item: ListMovieRespone) {
                // clear db
                val realm = Realm.getDefaultInstance()
//                Log.d(MovieReposTag,"Delete all movie")
//                movieDAO.deleteAllMovie(realm)

                Log.d(MovieReposTag,"Result size: "+item.results.size)
                for (movie in item.results){
                    val movieEntity = MovieEntity(movie.id, movie.poster_path, movie.adult,
                        movie.overview, movie.release_date, null, movie.original_title,
                        movie.original_language, movie.title, movie.backdrop_path, movie.popularity,
                        movie.vote_count, movie.video, movie.vote_average, category, false, page)
                    movieEntity.genreIds?.addAll(movie.genre_ids)
                    Log.d(MovieReposTag,"save movie "+movie.title)
                    movieDAO.saveMovie(movieEntity,realm)
                }
            }

            override fun shouldFetch(): Boolean {
                return true
//                Log.d(MovieReposTag,"Check internet: "+AppUtils.isOnline(context))
//                return AppUtils.isOnline(context)
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>>? {
                val listMoviesEntity = movieDAO.getMoviesByPage(page)
                if (listMoviesEntity.isNullOrEmpty()){
                    Log.d(MovieReposTag,"db empty")
                    return Flowable.empty()
                }
                // check data from db
                Log.d(MovieReposTag,"data from db size: "+listMoviesEntity.size)
                for (movie in listMoviesEntity){
                    Log.d(MovieReposTag,"data from db: "+movie.title)
                }

                return Flowable.fromArray(listMoviesEntity)
            }

            override fun createCall(): Observable<Resource<ListMovieRespone>> {
                Log.d(MovieReposTag,"Request now playing")
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
        }.result
    }

}