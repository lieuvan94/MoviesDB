package net.vinid.moviedb

import dagger.android.DaggerApplication
import net.vinid.moviedb.di.DaggerAppComponent

class MovieApplication: DaggerApplication(){

    private val appComponent = DaggerAppComponent.factory().create(this)

    override fun applicationInjector() = appComponent
}