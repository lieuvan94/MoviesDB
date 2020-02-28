package net.vinid.moviedb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.di.annotations.ViewModelKey
import net.vinid.moviedb.ui.home.HomeFragment
import net.vinid.moviedb.ui.home.MoviesViewModel

/*
* Dagger module for show movie by category feature
* */
@Module(includes = [MovieModule.ProvideViewModel::class])
abstract class MovieModule {

    @ContributesAndroidInjector(modules = [
        InjectMoviesViewModel::class
    ])
    internal abstract fun bindHomeFragment(): HomeFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(MoviesViewModel::class)
        fun provideMovieViewModel(repository: MovieRepository): ViewModel = MoviesViewModel(repository)
    }

    @Module
    class InjectMoviesViewModel{

        @Provides
        fun provideMoviesViewModel(factory: ViewModelProvider.Factory, fragment: HomeFragment):
            MoviesViewModel = ViewModelProvider(fragment, factory).get(MoviesViewModel::class.java)
    }
}