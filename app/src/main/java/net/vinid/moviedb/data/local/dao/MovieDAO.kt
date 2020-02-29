package net.vinid.moviedb.data.local.dao

import io.realm.RealmList
import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity

interface MovieDAO {
    fun getMoviesByPageAndCategory(page: Int, category: String): List<MovieEntity>
    fun getMoviesByGenre(page: Int, genre: Int): List<MovieEntity>
    fun getListGenres(): List<GenreEntity>
    fun saveListMovie(listMovie: List<MovieEntity>, category: String, page: Int)
    fun saveListGenres(listGenres: List<GenreEntity>)
    fun saveListMovieByGenres(genreId: Int, page: Int, listMovie: RealmList<MovieEntity>)
    fun updateMovieStatus(movieEntity: MovieEntity, isLike: Boolean)
    fun getMoviesLiked(): List<MovieEntity>
    fun getMoviesByPage(page: Int): List<MovieEntity>
    fun searchMoviesByQuery(query :String): List<MovieEntity>
}