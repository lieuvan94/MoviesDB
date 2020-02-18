package net.vinid.moviedb.util


import io.realm.RealmConfiguration
import io.realm.RealmList
import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.respone.GenreResponse
import net.vinid.moviedb.data.remote.respone.MovieResponse

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
    const val COLUMN_MOVIE_IS_LIKE = "isLike"
    const val COLUMN_MOVIE_ID = "movieId"
    const val COLUMN_GENRE_ID = "id"

    const val NETWORK_ERR_MES = "No internet connection. Switching to offline mode"

    const val BUNDLE_KEY_GENRE_ITEM = "genre_item"
    const val COLUMN_MOVIE_TITLE = "title"
    const val COLUMN_RECYCLEVIEW = 3
    const val QUERY_PAGE = 1
    const val REQUEST_DELAY = 500L

    fun convertMovieResponeToMovieEntity(movieResponse: List<MovieResponse>, category: String, page: Int)
            : List<MovieEntity> {
        return movieResponse.map {
            movie ->

            val realmListRenres = RealmList<Int>()
            realmListRenres.addAll(movie.genreIds)

            MovieEntity(movie.id.toString().plus(category), movie.id, movie.posterPath, movie.adult, movie.overview,
            movie.releaseDate, realmListRenres, movie.originalTitle,
            movie.originalLanguage, movie.title, movie.backdropPath, movie.popularity,
            movie.vote_count, movie.video, movie.vote_average, category, false, page)
        }
    }

    fun convertGenresResponeToGenresEntity(movieResponse: List<GenreResponse>): List<GenreEntity> {
        return movieResponse.map {
            GenreEntity(it.id, it.name)
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

