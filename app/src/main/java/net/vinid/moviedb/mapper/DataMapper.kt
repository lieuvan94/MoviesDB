package net.vinid.moviedb.mapper

import io.realm.RealmList
import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.model.GenreItem
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.data.remote.respone.GenreResponse
import net.vinid.moviedb.data.remote.respone.MovieResponse

fun List<GenreEntity>.toGenreItem() = map { GenreItem(it) }

fun List<MovieEntity>.toMovieItem() = map {
        movieEntity -> MovieItem(movieEntity,movieEntity.isLike)
}

fun List<MovieResponse>.toMovieEntity(category: String, page: Int) = map { movie ->
    val realmListRenres = RealmList<Int>()
    realmListRenres.addAll(movie.genreIds)

    MovieEntity(
        movie.id.toString().plus(category), movie.id, movie.posterPath, movie.adult, movie.overview,
        movie.releaseDate, realmListRenres, movie.originalTitle,
        movie.originalLanguage, movie.title, movie.backdropPath, movie.popularity,
        movie.vote_count, movie.video, movie.vote_average, category, false, page
    )
}

fun List<GenreResponse>.toGenreEntity() = map { GenreEntity(it.id, it.name) }
