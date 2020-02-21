package net.vinid.moviedb.data.repository

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.RealmList
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.api.APIService
import net.vinid.moviedb.data.remote.api.NetworkBoundResource
import net.vinid.moviedb.data.remote.api.Resource
import net.vinid.moviedb.data.remote.respone.ListGenresResponse
import net.vinid.moviedb.data.remote.respone.ListMovieResponse
import net.vinid.moviedb.util.AppUtils

class MovieRepositoryImpl(
    private val localDataSource: MovieDAO,
    private val remoteDataSource: APIService
) : MovieRepository {

    override fun getMovieByCategory(category: String, page: Int): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, ListMovieResponse>() {
            // save data from remote to local
            override fun saveCallResult(item: ListMovieResponse) {
                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(item.results, category, page)
                localDataSource.saveListMovie(listMovieEntity, category, page)
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>> {
                val list = localDataSource.getMoviesByPageAndCategory(page, category)
                if (list.isNullOrEmpty())
                    return Flowable.empty()
                return Flowable.fromArray(list)
            }

            // call API
            override fun createCall(): Observable<Resource<ListMovieResponse>> {
                // request by category
                return remoteDataSource.getMovieByCategory(category, page)
                    .flatMap { t -> Observable.just(Resource.success(t)) }
            }

            override fun convertRequestTypeToResultType(requestType: Resource<ListMovieResponse>)
                    : Resource<List<MovieEntity>> {
                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(requestType.data?.results!!, category, page)
                return Resource.success(listMovieEntity)
            }

        }.getResource()
    }

    //Todo: Get movie specification genre
    override fun getMovieByGenres(page: Int, genreId: Int): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, ListMovieResponse>(){
            override fun convertRequestTypeToResultType(requestType: Resource<ListMovieResponse>): Resource<List<MovieEntity>> {
                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(requestType.data?.results!!, "", page)
                return Resource.success(listMovieEntity)
            }

            override fun saveCallResult(item: ListMovieResponse) {
                val listMovieEntity = AppUtils
                    .convertMovieResponeToMovieEntity(item.results, "", page)

                val realmList = RealmList<MovieEntity>()
                realmList.addAll(listMovieEntity)

                localDataSource.saveListMovieByGenres(genreId, page, realmList)
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>> {
                val list = localDataSource.getMoviesByGenre(page, genreId)
                if (list.isNullOrEmpty())
                    return Flowable.empty()
                return Flowable.fromArray(list)
            }

            override fun createCall(): Observable<Resource<ListMovieResponse>> {
                return remoteDataSource.getMovieByGenres(page, genreId)
                    .flatMap { t -> Observable.just(Resource.success(t)) }
            }

        }.getResource()
    }

    override fun getListGenres(): Observable<Resource<List<GenreEntity>>> {
        return object : NetworkBoundResource<List<GenreEntity>, ListGenresResponse>(){
            override fun convertRequestTypeToResultType(requestType: Resource<ListGenresResponse>)
                    : Resource<List<GenreEntity>> {
                val listGenres= AppUtils
                    .convertGenresResponeToGenresEntity(requestType.data?.listGenres!!)
                return Resource.success(listGenres)
            }

            override fun saveCallResult(item: ListGenresResponse) {
                val listGenres = AppUtils.convertGenresResponeToGenresEntity(item.listGenres)
                localDataSource.saveListGenres(listGenres)
            }

            override fun loadFromDb(): Flowable<List<GenreEntity>> {
                val list = localDataSource.getListGenres()
                if (list.isNullOrEmpty())
                    return Flowable.empty()
                return Flowable.fromArray(list)
            }

            override fun createCall(): Observable<Resource<ListGenresResponse>> {
                return remoteDataSource.getListGenres()
                    .flatMap { t -> Observable.just(Resource.success(t)) }
            }

        }.getResource()
    }
}
