package net.vinid.moviedb.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import net.vinid.moviedb.MovieApplication
import net.vinid.moviedb.di.activitymodule.MainActivityBuilder
import net.vinid.moviedb.di.viewmodelmodule.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    ViewModelModule::class,
    MainActivityBuilder::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<MovieApplication>{

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}