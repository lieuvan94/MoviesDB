package net.vinid.moviedb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.vinid.moviedb.R
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.databinding.FragmentSearchByMovieNameBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.common.recycleview.EndlessRecyclerViewScrollListener
import net.vinid.moviedb.ui.home.MoviesAdapter
import net.vinid.moviedb.utils.ConstStrings
import javax.inject.Inject

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class SearchByMoviesNameFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchByMovieNameBinding
    private lateinit var searchMoviesAdapter: MoviesAdapter

    @Inject
    lateinit var searchMoviesViewModel: SearchMoviesViewModel

    private val sharedViewModel: SearchSharedViewModel by activityViewModels()

    lateinit var queue : String

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
            layoutManager = GridLayoutManager(context, ConstStrings.COLUMN_RECYCLEVIEW)
            adapter = this@SearchByMoviesNameFragment.searchMoviesAdapter
        }
    }

    private fun initViewModel() {
        sharedViewModel.keyword.observe(viewLifecycleOwner, Observer {
            queue = it
            searchMoviesViewModel.requestSearchMovieByPage(it,ConstStrings.QUERY_PAGE)
            searchMoviesViewModel.searchMovies.observe(viewLifecycleOwner, Observer {movies->
                updateMoviesList(movies)
            })
            loadMore()
        })
        searchMoviesViewModel.errSearchMovies.observe(viewLifecycleOwner, Observer {
            updateMoviesList(ArrayList())
        })
    }
    private fun updateMoviesList(movies: List<MovieItem>) {
        searchMoviesAdapter.setItems(movies)
    }

    private fun loadMore(){
        val scrollListener = object : EndlessRecyclerViewScrollListener(dataBinding.includedListMovieLayout.moviesRecyclerView.layoutManager
                as GridLayoutManager){
            override fun setLastPosition(view: RecyclerView) {
                view.scrollToPosition(dataBinding.includedListMovieLayout.moviesRecyclerView.adapter?.itemCount!!)
            }
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                searchMoviesViewModel.requestSearchMovieByPage(queue,page)
            }
        }
        dataBinding.includedListMovieLayout.moviesRecyclerView.addOnScrollListener(scrollListener)
    }

}