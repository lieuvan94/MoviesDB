package net.vinid.moviedb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.di.annotations.ViewModelKey
import net.vinid.moviedb.ui.favorites.FavoriteViewModel
import net.vinid.moviedb.ui.favorites.FavoritesFragment

/*
* Dagger module for show favorite movie feature
* */
@Module(includes = [FavoriteModule.ProvideViewModel::class])
abstract class FavoriteModule {

    @ContributesAndroidInjector(modules = [
        InjectFavoriteViewModel::class
    ])
    internal abstract fun bindFavoriteFragment(): FavoritesFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(FavoriteViewModel::class)
        fun provideFavoriteViewModel(repository: MovieRepository): ViewModel
                = FavoriteViewModel(repository)
    }

    @Module
    class InjectFavoriteViewModel{

        @Provides
        fun provideMoviesViewModel(factory: ViewModelProvider.Factory, fragment: FavoritesFragment):
                FavoriteViewModel = ViewModelProvider(fragment, factory).get(FavoriteViewModel::class.java)
    }
}