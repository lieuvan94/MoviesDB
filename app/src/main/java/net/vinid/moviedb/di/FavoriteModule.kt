package net.vinid.moviedb.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.vinid.moviedb.ui.favorites.FavoritesFragment

/*
* Dagger module for show favorite movie feature
* */
@Module
abstract class FavoriteModule{

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun favoriteFragment(): FavoritesFragment

}