package net.vinid.moviedb

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import net.vinid.moviedb.di.DaggerAppComponent

class MovieApplication:DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }

}