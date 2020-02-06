package net.vinid.moviedb.util


import io.realm.RealmConfiguration
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.remote.respone.MovieRespone

object AppUtils{
    const val CATEGORY_POPULAR = 1
    const val CATEGORY_NOW_PLAYING = 2
    const val CATEGORY_UPCOMING = 3
    const val CATEGORY_TOP_RATES = 4

    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500%s"
    private const val DB_NAME = "moviedb.realm"

    fun convertMovieResponeToMovieEntity(movieRespone: List<MovieRespone>, category: Int, page: Int)
            : ArrayList<MovieEntity> {
        val listMovieEntity = ArrayList<MovieEntity>()
        for (movie in movieRespone) {
            val movieEntity = MovieEntity(
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

