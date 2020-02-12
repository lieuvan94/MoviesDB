package net.vinid.moviedb.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentSearchBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.search.SearchViewPagerAdapter

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchBinding
    private lateinit var searchViewPagerAdapter: SearchViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        dataBinding.lifecycleOwner = this

        searchViewPagerAdapter = SearchViewPagerAdapter(childFragmentManager)
        dataBinding.moviesResultViewPager.adapter = searchViewPagerAdapter
        dataBinding.searchMoviesTabLayout.setupWithViewPager(dataBinding.moviesResultViewPager)

        return dataBinding.root
    }
}