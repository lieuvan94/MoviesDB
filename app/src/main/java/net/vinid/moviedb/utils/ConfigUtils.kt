package net.vinid.moviedb.utils

import io.realm.RealmConfiguration

object ConfigUtils {
    private const val DB_NAME = "moviedb.realm"

    fun initRealmConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(DB_NAME)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
    }
}