package net.vinid.moviedb.data.model

import net.vinid.moviedb.data.local.entity.GenreEntity
import java.io.Serializable

data class GenreItem (
    val genreEntity: GenreEntity
) : Serializable