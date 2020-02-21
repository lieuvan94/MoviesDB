package net.vinid.moviedb.data.mapper

import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.model.GenreItem
import net.vinid.moviedb.data.model.MovieItem

fun List<GenreEntity>.toGenreItem() = map {
        genreEntity -> GenreItem(genreEntity)
}

fun List<MovieEntity>.toMovieItem() = map {
        movieEntity -> MovieItem(movieEntity,movieEntity.isLike)
}

