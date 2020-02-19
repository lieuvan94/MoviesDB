package net.vinid.moviedb.data.local.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GenreEntity (
    @PrimaryKey
    var id : Int = 0,
    var name : String = "",
    var listMovie: RealmList<MovieEntity> = RealmList()
):RealmObject()