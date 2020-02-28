package net.vinid.moviedb.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import net.vinid.moviedb.di.annotations.ViewModelKey
import net.vinid.moviedb.ui.home.HomeFragment
import net.vinid.moviedb.ui.home.MoviesViewModel

/*
* Dagger module for show movie by category feature
* */
@Module
abstract class MovieModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun homeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    internal abstract fun bindViewModel(viewmodel: MoviesViewModel): ViewModel
}