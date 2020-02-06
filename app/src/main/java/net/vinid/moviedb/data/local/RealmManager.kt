package net.vinid.moviedb.data.local

import io.realm.Realm
import io.realm.RealmConfiguration
import net.vinid.moviedb.data.local.dao.MovieDAO

object  RealmManager {
    lateinit var realmInstance: Realm

    fun createMovieDAO(): MovieDAO {
        val realmConfiguration = RealmConfiguration.Builder()
            .name("movie.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()

        realmInstance = Realm.getInstance(realmConfiguration)
        return MovieDAO(realmInstance)
    }

}