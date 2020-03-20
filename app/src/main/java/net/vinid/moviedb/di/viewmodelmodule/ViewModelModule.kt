package net.vinid.moviedb.di.viewmodelmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.vinid.moviedb.ui.favorites.FavoriteViewModel
import net.vinid.moviedb.ui.genres.GenresViewModel
import net.vinid.moviedb.ui.home.MoviesViewModel
import net.vinid.moviedb.ui.search.SearchMoviesViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    fun bindMoviesViewModel(
        dataViewModel: MoviesViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GenresViewModel::class)
    fun bindGenresViewModel(
        genresViewModel: GenresViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    fun bindFavoriteViewModel(
        favoriteViewModel: FavoriteViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchMoviesViewModel::class)
    fun bindSearchMoviesViewModel(
        searchMoviesViewModel: SearchMoviesViewModel
    ): ViewModel

}
