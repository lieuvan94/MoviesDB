package net.vinid.moviedb.di.activitymodule

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.vinid.moviedb.ui.favorites.FavoritesFragment
import net.vinid.moviedb.ui.genres.GenreMovieFragment
import net.vinid.moviedb.ui.home.HomeFragment
import net.vinid.moviedb.ui.search.SearchByMoviesNameFragment
import net.vinid.moviedb.ui.search.SearchFragment
import net.vinid.moviedb.ui.search.SearchMoviesByCategoryFragment
import net.vinid.moviedb.ui.search.SearchMoviesByGenresFragment

/**
 * Created by Nguyen Van Lieu on 3/3/2020.
 */

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun contributeHomeFragment() : HomeFragment

    @ContributesAndroidInjector
    fun contributeFavoritesFragment() : FavoritesFragment

    @ContributesAndroidInjector
    fun contributeGenreMovieFragment() : GenreMovieFragment

    @ContributesAndroidInjector
    fun contributeSearchByMoviesNameFragment() : SearchByMoviesNameFragment

    @ContributesAndroidInjector
    fun contributeSearchFragment() : SearchFragment

    @ContributesAndroidInjector
    fun contributeSearchMoviesByCategoryFragment() : SearchMoviesByCategoryFragment

    @ContributesAndroidInjector
    fun contributeSearchMoviesByGenresFragment() : SearchMoviesByGenresFragment
}