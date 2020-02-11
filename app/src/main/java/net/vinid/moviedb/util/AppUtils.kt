package net.vinid.moviedb.util


import io.realm.RealmConfiguration
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

    fun convertMovieResponeToMovieEntity(movieRespone: List<MovieRespone>, category: String, page: Int)
            : ArrayList<MovieEntity> {
        val listMovieEntity = ArrayList<MovieEntity>()
        for (movie in movieRespone) {
            val movieEntity = MovieEntity(
                0,
                movie.id, movie.poster_path, movie.adult,
                movie.overview, movie.release_date, null, movie.original_title,
                movie.original_language, movie.title, movie.backdrop_path, movie.popularity,
                movie.vote_count, movie.video, movie.vote_average, category, false, page
            )
            movieEntity.genreIds?.addAll(movie.genre_ids)
            listMovieEntity.add(movieEntity)
        }
        return listMovieEntity
    }

    fun initRealmConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(DB_NAME)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
    }
}

