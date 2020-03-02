package net.vinid.moviedb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.di.annotations.ViewModelKey
import net.vinid.moviedb.ui.search.SearchByMoviesNameFragment
import net.vinid.moviedb.ui.search.SearchFragment
import net.vinid.moviedb.ui.search.SearchMoviesViewModel

/*
* Dagger module for show movie by search feature
* */
@Module(includes = [SearchMovieModule.ProvideViewModel::class])
abstract class SearchMovieModule {

    @ContributesAndroidInjector(modules = [
        InjectSearchMoviesViewModel::class
    ])

    internal abstract fun bindSearchFragment(): SearchFragment

    internal abstract fun bindSearchByMoviesNameFragment(): SearchByMoviesNameFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SearchMoviesViewModel::class)
        fun provideSearchMoviesViewModel(repository: MovieRepository): ViewModel = SearchMoviesViewModel(repository)
    }

    @Module
    class InjectSearchMoviesViewModel{

        @Provides
        fun provideSearchMoviesViewModel(factory: ViewModelProvider.Factory, fragment: SearchByMoviesNameFragment):
                SearchMoviesViewModel = ViewModelProvider(fragment, factory).get(SearchMoviesViewModel::class.java)
    }
}