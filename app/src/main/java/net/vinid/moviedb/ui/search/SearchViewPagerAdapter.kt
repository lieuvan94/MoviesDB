package net.vinid.moviedb.ui.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.vinid.moviedb.ui.home.HomeFragment
import net.vinid.moviedb.ui.search.view.SearchByMoviesNameFragment
import net.vinid.moviedb.ui.search.view.SearchMoviesByCategoryFragment
import net.vinid.moviedb.ui.search.view.SearchMoviesByGenresFragment

/**
 * Created by Nguyen Van Lieu on 2/11/2020.
 */
class SearchViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object{
        const val NUM_PAGER = 3
        const val MOVIES_NAME = "Movies name"
        const val CATEGORY = "Category"
        const val GENRES = "Genres"
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> SearchByMoviesNameFragment()
        1 -> SearchMoviesByCategoryFragment()
        2 -> SearchMoviesByGenresFragment()
        else -> SearchByMoviesNameFragment()
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> MOVIES_NAME
        1 -> CATEGORY
        2 -> GENRES
        else -> ""
    }

    override fun getCount(): Int = NUM_PAGER

}