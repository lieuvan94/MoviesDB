package net.vinid.moviedb.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentSearchBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.search.SearchMoviesViewModel
import net.vinid.moviedb.ui.search.SearchViewPagerAdapter

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchBinding
    private lateinit var searchViewPagerAdapter: SearchViewPagerAdapter

    private val searchMoviesViewModel: SearchMoviesViewModel by lazy {
        ViewModelProvider(this).get(SearchMoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        dataBinding.lifecycleOwner = this

        initView()

        return dataBinding.root
    }

    private fun initView(){
        searchViewPagerAdapter = SearchViewPagerAdapter(childFragmentManager)
        dataBinding.moviesResultViewPager.adapter = searchViewPagerAdapter
        dataBinding.searchMoviesTabLayout.setupWithViewPager(dataBinding.moviesResultViewPager)

        dataBinding.svSearchMovies.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    querySearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun querySearch(query: String) {

        searchMoviesViewModel.requestSearchMovieByPage(query,1)

    }
}