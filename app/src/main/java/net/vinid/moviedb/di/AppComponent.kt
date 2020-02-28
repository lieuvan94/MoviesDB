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
    ApplicationModule::class,
    AndroidInjectionModule::class,
    MovieModule::class,
    GenreModule::class,
    FavoriteModule::class,
    MovieSearchModule::class])
interface AppComponent : AndroidInjector<MovieApplication>{

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}