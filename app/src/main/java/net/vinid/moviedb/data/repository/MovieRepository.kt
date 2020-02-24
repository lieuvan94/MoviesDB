package net.vinid.moviedb.data.repository

import io.reactivex.Observable
import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.api.Resource

interface MovieRepository {
    fun getMovieByCategory(category: String, page: Int): Observable<Resource<List<MovieEntity>>>
    fun getMovieByGenres(page: Int, genreId: Int): Observable<Resource<List<MovieEntity>>>
    fun getListGenres(): Observable<Resource<List<GenreEntity>>>
    fun updateMovieStatus(movie: MovieEntity, isLike: Boolean)
    fun getMoviesLiked(): Observable<Resource<List<MovieEntity>>>
}