package net.vinid.moviedb.data.local.dao

import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity

interface MovieDAO {
    fun getMoviesByPageAndCategory(page: Int, category: String): List<MovieEntity>
    fun getMoviesByGenre(page: Int, genre: Int): List<MovieEntity>
    fun getListGenres(): List<GenreEntity>
    fun saveListMovie(listMovie: List<MovieEntity>, category: String, page: Int)
    fun saveListGenres(listMovie: List<GenreEntity>)
}