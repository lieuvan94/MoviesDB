package net.vinid.moviedb.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.vinid.moviedb.ui.genres.GenreMovieFragment

/*
* Dagger module for show movie by genre feature
* */
@Module
abstract class GenreModule{

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun genreFragment(): GenreMovieFragment

}