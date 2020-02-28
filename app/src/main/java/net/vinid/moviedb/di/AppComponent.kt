package net.vinid.moviedb.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import net.vinid.moviedb.MovieApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModule::class,
    AndroidInjectionModule::class,
    UIModule::class])
interface AppComponent : AndroidInjector<MovieApplication>{

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}