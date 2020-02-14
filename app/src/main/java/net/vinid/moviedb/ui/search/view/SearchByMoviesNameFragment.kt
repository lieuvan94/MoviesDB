package net.vinid.moviedb.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import net.vinid.moviedb.R
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.databinding.FragmentSearchByMovieNameBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.home.MoviesAdapter
import net.vinid.moviedb.ui.search.SearchMoviesViewModel
import net.vinid.moviedb.util.AppUtils

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchByMoviesNameFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchByMovieNameBinding
    private lateinit var searchMoviesAdapter: MoviesAdapter

    private val searchMoviesViewModel: SearchMoviesViewModel by lazy {
        ViewModelProvider(this).get(SearchMoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_by_movie_name, container, false)
        dataBinding.lifecycleOwner = this

        initView()
        initViewModel()

        return dataBinding.root
    }

    private fun initView() {
        searchMoviesAdapter = MoviesAdapter()
        dataBinding.includedListMovieLayout.moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(context,AppUtils.COLUMN_RECYCLEVIEW)
            adapter = this@SearchByMoviesNameFragment.searchMoviesAdapter
        }
    }

    private fun initViewModel() {
        searchMoviesViewModel.searchMovies.observe(viewLifecycleOwner, Observer {
            updateMoviesList(it)
        })

    }

    private fun updateMoviesList(movies: List<MovieEntity>) {
//        searchMoviesAdapter.setItems(movies)
    }
}