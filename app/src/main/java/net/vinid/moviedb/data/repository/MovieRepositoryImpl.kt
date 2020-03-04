package net.vinid.moviedb.data.repository

import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.RealmList
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.mapper.toGenreEntity
import net.vinid.moviedb.data.mapper.toMovieEntity
import net.vinid.moviedb.data.remote.api.APIService
import net.vinid.moviedb.data.remote.api.NetworkBoundResource
import net.vinid.moviedb.data.remote.api.Resource
import net.vinid.moviedb.data.remote.respone.ListGenresResponse
import net.vinid.moviedb.data.remote.respone.ListMovieResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
     private val localDataSource: MovieDAO,
     private val remoteDataSource: APIService
) : MovieRepository {

    override fun getMovieByCategory(category: String, page: Int): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, ListMovieResponse>() {
            // save data from remote to local
            override fun saveCallResult(item: ListMovieResponse) {
                val listMovieEntity = item.results.toMovieEntity(category, page)
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
                val listMovieEntity = requestType.data?.results!!
                    .toMovieEntity(category, page)

                return Resource.success(listMovieEntity)
            }

        }.getResource()
    }

    override fun getMovieByGenres(page: Int, genreId: Int): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, ListMovieResponse>(){
            override fun convertRequestTypeToResultType(requestType: Resource<ListMovieResponse>): Resource<List<MovieEntity>> {
                val listMovieEntity = requestType.data?.results!!
                    .toMovieEntity("", page)
                return Resource.success(listMovieEntity)
            }

            override fun saveCallResult(item: ListMovieResponse) {
                val listMovieEntity = item.results.toMovieEntity("", page)

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
                val listGenres= requestType.data?.listGenres!!.toGenreEntity()
                return Resource.success(listGenres)
            }

            override fun saveCallResult(item: ListGenresResponse) {
                val listGenres = item.listGenres.toGenreEntity()
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

    override fun updateMovieStatus(movie: MovieEntity, isLike: Boolean){
        localDataSource.updateMovieStatus(movie, isLike)
    }

    override fun getMoviesLiked(): Observable<Resource<List<MovieEntity>>> {
        return Observable.just(Resource.success(localDataSource.getMoviesLiked()))
    }

    override fun searchMoviesByQuery(query: String, page: Int): Observable<List<MovieEntity>> {
        return remoteDataSource.searchMoviesByQuery(query,page)
            .map {
                it.results.toMovieEntity("",page)
            }.onErrorResumeNext(
                Observable.just(localDataSource.searchMoviesByQuery(query))
            )
    }
}
