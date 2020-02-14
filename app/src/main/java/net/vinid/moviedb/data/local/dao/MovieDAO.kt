package net.vinid.moviedb.data.local.dao

import net.vinid.moviedb.data.local.entity.MovieEntity

interface MovieDAO {
    fun getMoviesByPageAndCategory(page: Int, category: String): List<MovieEntity>
    fun getMoviesByPage(page: Int): List<MovieEntity>
    fun searchMoviesByQuery(query :String): List<MovieEntity>
    fun saveListMovie(listMovie: List<MovieEntity>, category: String, page: Int)
    fun deleteMovieByPageAndCategory(page: Int, category: String)
    fun saveListMovie(listMovie: List<MovieEntity>)
    fun getMoviesByPage(page: Int): List<MovieEntity>
    fun searchMoviesByQuery(query :String): List<MovieEntity>
}