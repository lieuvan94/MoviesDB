package net.vinid.moviedb.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import net.vinid.moviedb.MovieApplication
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

    private val viewModelFactory = MovieApplication.injectViewModelFactory()

    private val searchMoviesViewModel: SearchMoviesViewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(SearchMoviesViewModel::class.java)
    }

    private val sharedViewModel: SearchSharedViewModel by activityViewModels()

    private  lateinit var query: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_by_movie_name, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = searchMoviesViewModel

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
        sharedViewModel.keyword.observe(viewLifecycleOwner, Observer {
            query = it
            searchMoviesViewModel.requestSearchMovieByPage(query,1)
        })
        searchMoviesViewModel.searchMovies.observe(viewLifecycleOwner, Observer {
            updateMoviesList(it)
        })
    }
    private fun updateMoviesList(movies: List<MovieEntity>) {
        searchMoviesAdapter.setItems(movies)
    }

}