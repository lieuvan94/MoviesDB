package net.vinid.moviedb.data.local.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MovieEntity(
    @PrimaryKey
    var id: String? = null,
    var movieId: Int = 0,
    var posterPath: String? = null,
    var adult: Boolean = false,
    var overview: String? = null,
    var releaseDate: String? = null,
    var genreIds: RealmList<Int>? = RealmList(),
    var originalTitle: String? = null,
    var originalLanguage: String? = null,
    var title: String? = null,
    var backdropPath: String? = null,
    var popularity: Double? = null,
    var voteCount: Int? = null,
    var video: Boolean? = null,
    var voteAverage: Double? = null,
    var category: String? = null,
    var isLike: Boolean = false,
    var page: Int? = null
) : RealmObject()


