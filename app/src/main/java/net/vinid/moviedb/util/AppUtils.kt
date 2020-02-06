package net.vinid.moviedb.util.ext

import android.content.Context
import android.net.ConnectivityManager
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.respone.ListMovieRespone
import net.vinid.moviedb.data.remote.respone.MovieRespone
import net.vinid.moviedb.data.repository.MovieRepository

object AppUtils{
    const val CATEGORY_POPULAR = 1
    const val CATEGORY_NOW_PLAYING = 2
    const val CATEGORY_UPCOMING = 3
    const val CATEGORY_TOP_RATES = 4

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun convertMovieResponeToMovieEntity(movieRespone: List<MovieRespone>, category: Int, page: Int)
            : ArrayList<MovieEntity>{
        val listMovieEntity = ArrayList<MovieEntity>()
        for (movie in movieRespone){
            val movieEntity = MovieEntity(movie.id, movie.poster_path, movie.adult,
                movie.overview, movie.release_date, null, movie.original_title,
                movie.original_language, movie.title, movie.backdrop_path, movie.popularity,
                movie.vote_count, movie.video, movie.vote_average, category, false, page)
            movieEntity.genreIds?.addAll(movie.genre_ids)
            listMovieEntity.add(movieEntity)
        }
        return listMovieEntity
    }
}

