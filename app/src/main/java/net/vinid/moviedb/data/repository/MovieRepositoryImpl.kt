package net.vinid.moviedb.data.repository

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Observable
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.api.APIService
import net.vinid.moviedb.data.remote.api.NetworkBoundResource
import net.vinid.moviedb.data.remote.api.Resource
import net.vinid.moviedb.data.remote.respone.ListMovieRespone
import net.vinid.moviedb.util.AppUtils
import net.vinid.moviedb.util.NetworkManager

class MovieRepositoryImpl(
    context: Context,
    private val localDataSource: MovieDAO,
    private val remoteDataSource: APIService
) : MovieRepository {
    private val networkManager: NetworkManager = NetworkManager(context)

    override fun getMovieByCategory(category: String, page: Int): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, ListMovieRespone>(){

            override fun deleteMovieByPageAndCategory() {
                localDataSource.deleteMovieByPageAndCategory(page,category)
            }

            // save data from remote to local
            override fun saveCallResult(item: ListMovieRespone) {
                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(item.results, category, page)
                localDataSource.saveListMovie(listMovieEntity)
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>>? {
                val list = localDataSource.getMoviesByPageAndCategory(page,category)
                if (list.isNullOrEmpty())
                    return Flowable.empty()
                return Flowable.fromArray(list)
            }

            // call API
            override fun createCall(): Observable<Resource<ListMovieRespone>> {
                // request by category
                return remoteDataSource.getMovieByCategory(category, page)
                    .flatMap { t -> Observable.just(Resource.success(t)) }
            }

            override fun convertRequestTypeToResultType(requestType: Resource<ListMovieRespone>)
                    : Resource<List<MovieEntity>> {
                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(requestType.data?.results!!, category, page)
                return Resource.success(listMovieEntity)
            }

            override fun shouldFetch(): Boolean {
                // check internet
                return networkManager.isAvailable
            }
        }.result

    }

}