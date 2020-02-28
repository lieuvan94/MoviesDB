package net.vinid.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_category_movies_list.view.*
import net.vinid.moviedb.MainActivity
import net.vinid.moviedb.R
import net.vinid.moviedb.data.model.GenreItem
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.databinding.FragmentHomeBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.common.recycleview.EndlessRecyclerViewScrollListener
import net.vinid.moviedb.ui.genres.GenresAdapter
import net.vinid.moviedb.utils.ConstStrings.Companion.BUNDLE_KEY_GENRE_ITEM
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_NOW_PLAYING
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_POPULAR
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_TOP_RATES
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_UPCOMING
import javax.inject.Inject

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class HomeFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val moviesViewModel by viewModels<MoviesViewModel> { viewModelFactory }

    private lateinit var popularMovieAdapter: MoviesAdapter
    private lateinit var upComingMovieAdapter: MoviesAdapter
    private lateinit var topRateMovieAdapter: MoviesAdapter
    private lateinit var nowPlayingMovieAdapter: MoviesAdapter

    private lateinit var genresAdapter: GenresAdapter

    private val firstPage = 1

    private var popularListState: Int = 0
    private var upComingListState: Int = 0
    private var topRateListState: Int = 0
    private var nowPlayingListState: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        dataBinding.lifecycleOwner = this

        initView()
        initViewModel()
        initLoadMore()
        initSwipeToRefresh()

        moviesViewModel.requestGetListGenres()

        return dataBinding.root
    }

    private fun initSwipeToRefresh() {
        dataBinding.swipeRefresh.setOnRefreshListener {
            popularMovieAdapter.clearItem()
            upComingMovieAdapter.clearItem()
            nowPlayingMovieAdapter.clearItem()
            topRateMovieAdapter.clearItem()

            includedPopularMovieLayout.moviesRecyclerView.clearOnScrollListeners()
            includedNowPlayingMovieLayout.moviesRecyclerView.clearOnScrollListeners()
            includedUpComingMovieLayout.moviesRecyclerView.clearOnScrollListeners()
            includedTopRateMovieLayout.moviesRecyclerView.clearOnScrollListeners()

            initLoadMore()

            swipeRefresh.isRefreshing = false
        }
    }

    private fun initLoadMore(){
        initLoadMore(dataBinding.includedPopularMovieLayout.moviesRecyclerView, MOVIE_POPULAR)

        initLoadMore(dataBinding.includedNowPlayingMovieLayout.moviesRecyclerView, MOVIE_NOW_PLAYING)

        initLoadMore(dataBinding.includedUpComingMovieLayout.moviesRecyclerView, MOVIE_UPCOMING)

        initLoadMore(dataBinding.includedTopRateMovieLayout.moviesRecyclerView, MOVIE_TOP_RATES)
    }

    override fun onPause() {
        super.onPause()
        saveListSate()
    }

    override fun onResume() {
        super.onResume()
        getListState()
    }

    private fun requestGetMovie(category: String, page: Int) {
        moviesViewModel.requestGetMovieByPage(category, page)
    }

    private fun initLoadMore(recyclerView: RecyclerView, category: String){
        // first load data
        requestGetMovie(category, firstPage)

        val scrollListener = object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
            override fun setLastPosition(view: RecyclerView) {
                view.scrollToPosition(recyclerView.adapter?.itemCount!!)
            }

            override fun onLoadMore(
                page: Int, totalItemsCount: Int, view: RecyclerView?
            ) {
                requestGetMovie(category, page)
            }
        }

        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun initView(){
        popularMovieAdapter = MoviesAdapter()
        upComingMovieAdapter = MoviesAdapter()
        nowPlayingMovieAdapter = MoviesAdapter()
        topRateMovieAdapter = MoviesAdapter()

        dataBinding.includedPopularMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.popularMovieAdapter
        dataBinding.includedNowPlayingMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.nowPlayingMovieAdapter
        dataBinding.includedUpComingMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.upComingMovieAdapter
        dataBinding.includedTopRateMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.topRateMovieAdapter

        genresAdapter = GenresAdapter()
        dataBinding.genresRecyclerView.adapter = this@HomeFragment.genresAdapter

        genresAdapter.onItemClick = {
            showMovieByGenre(it)
        }

        popularMovieAdapter.onItemClick = {
            updateMovieStatus(it)
        }

        nowPlayingMovieAdapter.onItemClick = {
            updateMovieStatus(it)
        }

        upComingMovieAdapter.onItemClick = {
            updateMovieStatus(it)
        }

        topRateMovieAdapter.onItemClick = {
            updateMovieStatus(it)
        }


    }

    private fun updateMovieStatus(movieItem: MovieItem){
        popularMovieAdapter.changeMovieFavoriteStatus(movieItem.movieEntity.movieId)
        nowPlayingMovieAdapter.changeMovieFavoriteStatus(movieItem.movieEntity.movieId)
        upComingMovieAdapter.changeMovieFavoriteStatus(movieItem.movieEntity.movieId)
        topRateMovieAdapter.changeMovieFavoriteStatus(movieItem.movieEntity.movieId)
        moviesViewModel.requestUpdateMovieStatus(movieItem.movieEntity, movieItem.favoriteStatus)
        val rootView = activity as MainActivity
    }

    private fun showMovieByGenre(genre: GenreItem) {
        val bundle = Bundle()
        bundle.putSerializable(BUNDLE_KEY_GENRE_ITEM, genre)

        this.findNavController().navigate(
            R.id.genreScreen, bundle
        )
    }

    private fun initViewModel() {
        moviesViewModel.popularMovie.observe(viewLifecycleOwner, Observer {
            updateListMovie(it, popularMovieAdapter)
        })

        moviesViewModel.upComingMovie.observe(viewLifecycleOwner, Observer {
            updateListMovie(it, upComingMovieAdapter)
        })

        moviesViewModel.topRatesMovie.observe(viewLifecycleOwner, Observer {
            updateListMovie(it, topRateMovieAdapter)
        })

        moviesViewModel.nowPlayingMovie.observe(viewLifecycleOwner, Observer {
            updateListMovie(it, nowPlayingMovieAdapter)
        })

        moviesViewModel.genres.observe(viewLifecycleOwner, Observer {
            updateListGenres(it)
        })

        moviesViewModel.errorGetData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { it ->
                val rootView = activity as MainActivity
                rootView.showMes(it.message!!)
            }
        })

    }

    private fun updateListMovie(movies: ArrayList<MovieItem>, adapter: MoviesAdapter) {
        adapter.setItems(movies)
    }

    private fun updateListGenres(genres: List<GenreItem>) {
        genresAdapter.setItems(genres)
    }

    private fun saveListSate(){
        popularListState = (includedPopularMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstCompletelyVisibleItemPosition()
        upComingListState = (includedUpComingMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstCompletelyVisibleItemPosition()
        topRateListState = (includedTopRateMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstCompletelyVisibleItemPosition()
        nowPlayingListState = (includedNowPlayingMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstCompletelyVisibleItemPosition()
    }

    private fun getListState(){
        includedPopularMovieLayout.moviesRecyclerView.scrollToPosition(popularListState)
        includedUpComingMovieLayout.moviesRecyclerView.scrollToPosition(upComingListState)
        includedTopRateMovieLayout.moviesRecyclerView.scrollToPosition(topRateListState)
        includedNowPlayingMovieLayout.moviesRecyclerView.scrollToPosition(nowPlayingListState)
    }
}

