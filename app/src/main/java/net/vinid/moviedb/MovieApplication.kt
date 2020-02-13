package net.vinid.moviedb

import android.app.Application
import io.realm.Realm
import net.vinid.moviedb.util.AppUtils

class MovieApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(AppUtils.initRealmConfig())
    }
}