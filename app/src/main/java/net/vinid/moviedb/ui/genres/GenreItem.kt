package net.vinid.moviedb.ui.genres

import net.vinid.moviedb.data.local.entity.GenreEntity
import java.io.Serializable

data class GenreItem (
    val genreEntity: GenreEntity
) : Serializable