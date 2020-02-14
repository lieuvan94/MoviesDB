package net.vinid.moviedb.data.repository

import io.reactivex.Observable
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.api.Resource
import net.vinid.moviedb.data.remote.respone.MovieRespone

interface MovieRepository {
    fun getMovieByCategory(category: String, page: Int): Observable<Resource<List<MovieEntity>>>
    fun searchMoviesByQuery(query: String, page: Int): Observable<List<MovieRespone>>
}