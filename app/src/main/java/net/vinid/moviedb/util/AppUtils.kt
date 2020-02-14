package net.vinid.moviedb.util


import io.realm.RealmConfiguration
import io.realm.RealmList
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.respone.MovieRespone

object AppUtils{

    const val MOVIE_POPULAR = "popular"
    const val MOVIE_NOW_PLAYING = "now_playing"
    const val MOVIE_UPCOMING = "upcoming"
    const val MOVIE_TOP_RATES = "top_rated"

    const val IMAGE_URL = "https://image.tmdb.org/t/p/w200"
    const val BASE_MOVIE_URL = "https://api.themoviedb.org/3/"
    const val QUERY_API_KEY = "api_key"
    const val TMDB_API_KEY = "481db3e563055debea2b91c8a74a71b7"

    private const val DB_NAME = "moviedb.realm"
    const val COLUMN_PAGE = "page"
    const val COLUMN_MOVIE_CATEGORY = "category"
    const val COLUMN_MOVIE_TITLE = "title"
    const val COLUMN_RECYCLEVIEW = 3

    fun convertMovieResponeToMovieEntity(movieRespone: List<MovieRespone>, category: String, page: Int)
            : List<MovieEntity> {
        return movieRespone.map {
            movie -> MovieEntity(0, movie.id, movie.posterPath, movie.adult, movie.overview,
            movie.releaseDate, movie.genreIds as RealmList<Int>, movie.originalTitle,
            movie.originalLanguage, movie.title, movie.backdropPath, movie.popularity,
            movie.vote_count, movie.video, movie.vote_average, category, false, page)
        }
    }

    fun convertMovieEntityToMovieRespone(movieEntity: List<MovieEntity>)
            : List<MovieRespone> {
        return movieEntity.map {
                movie -> MovieRespone(movie.posterPath!!,movie.adult, movie.overview!!, movie.releaseDate!!,
            movie.genreIds as List<Int>,movie.id,movie.originalTitle!!,movie.originalLanguage!!,movie.originalTitle!!,
            movie.backdropPath!!,movie.popularity!!,movie.voteCount!!,movie.video!!,movie.voteAverage!!)
        }
    }


    fun initRealmConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(DB_NAME)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
    }
}

