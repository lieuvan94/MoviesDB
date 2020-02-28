package net.vinid.moviedb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.di.annotations.ViewModelKey
import net.vinid.moviedb.ui.genres.GenreMovieFragment
import net.vinid.moviedb.ui.genres.GenreViewModel

/*
* Dagger module for show movie by genre feature
* */
@Module(includes = [GenreModule.ProvideViewModel::class])
abstract class GenreModule {

    @ContributesAndroidInjector(modules = [
        InjectGenreViewModel::class
    ])
    internal abstract fun bindGenreFragment(): GenreMovieFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(GenreViewModel::class)
        fun provideGenreViewModel(repository: MovieRepository): ViewModel = GenreViewModel(repository)
    }

    @Module
    class InjectGenreViewModel{

        @Provides
        fun provideGenreViewModel(factory: ViewModelProvider.Factory, fragment: GenreMovieFragment):
                GenreViewModel = ViewModelProvider(fragment, factory).get(GenreViewModel::class.java)
    }
}