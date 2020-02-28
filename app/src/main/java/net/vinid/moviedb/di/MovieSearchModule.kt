package net.vinid.moviedb.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.vinid.moviedb.ui.search.SearchFragment

/*
* Dagger module for search movie feature
* */
@Module
abstract class MovieSearchModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun searchFragment(): SearchFragment
}