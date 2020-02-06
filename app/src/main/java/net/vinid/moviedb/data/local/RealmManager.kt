package net.vinid.moviedb.data.local

import io.realm.Realm
import net.vinid.moviedb.data.local.dao.MovieDAO
import net.vinid.moviedb.util.AppUtils

object  RealmManager {
    private lateinit var realmInstance: Realm

    fun createMovieDAO(): MovieDAO {
        realmInstance = Realm.getInstance(AppUtils.initRealmConfig())
        return MovieDAO(realmInstance)
    }
}