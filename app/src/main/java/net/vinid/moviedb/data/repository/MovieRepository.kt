package net.vinid.moviedb.data.repository

import io.reactivex.Observable
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.api.Resource

interface MovieRepository {
    fun getMovieByCategory(category: String, page: Int): Observable<Resource<List<MovieEntity>>>
}