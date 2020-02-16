package net.vinid.moviedb.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_category_movies_list.view.*
import net.vinid.moviedb.R
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.databinding.FragmentHomeBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.common.recycleview.EndlessRecyclerViewScrollListener
import net.vinid.moviedb.util.AppUtils


/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class HomeFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentHomeBinding

    private lateinit var moviesViewModel: MoviesViewModel

//    private val genresViewModel: GenresViewModel by lazy {
//        ViewModelProviders.of(this).get(GenresViewModel::class.java)
//    }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        dataBinding.lifecycleOwner = this

        initView()
        initViewModel()

        return dataBinding.root
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

    private fun initLoadMore(recyclerView: RecyclerView
                             , linearLayoutManager: LinearLayoutManager, category: String){
        // first load data
        requestGetMovie(category, firstPage)

        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun setLastPosition(view: RecyclerView) {
                view.scrollToPosition(recyclerView.adapter?.itemCount!!)
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?
            ) {
                Handler().postDelayed({
                    requestGetMovie(category, page)
                }, AppUtils.TIME_WAIT_LOAD_DATA)
            }
        }

        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun initView(){
        dataBinding.includedPopularMovieLayout.movieCategoryTitle.setText(R.string.popular_movies_label)
        dataBinding.includedNowPlayingMovieLayout.movieCategoryTitle.setText(R.string.now_playing_movies_label)
        dataBinding.includedUpComingMovieLayout.movieCategoryTitle.setText(R.string.upcoming_movies_label)
        dataBinding.includedTopRateMovieLayout.movieCategoryTitle.setText(R.string.top_rates_movies_label)

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

    }

    private fun initViewModel() {
        moviesViewModel = MoviesViewModel(activity!!.application)
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)



        moviesViewModel.popularMovie.observe(viewLifecycleOwner, Observer {
            updateMoviesList(it, popularMovieAdapter, AppUtils.MOVIE_POPULAR)
        })

        moviesViewModel.upComingMovie.observe(viewLifecycleOwner, Observer {
            updateMoviesList(it, upComingMovieAdapter, AppUtils.MOVIE_UPCOMING)
        })

        moviesViewModel.topRatesMovie.observe(viewLifecycleOwner, Observer {
            updateMoviesList(it, topRateMovieAdapter, AppUtils.MOVIE_TOP_RATES)
        })

        moviesViewModel.nowPlayingMovie.observe(viewLifecycleOwner, Observer {
            updateMoviesList(it, nowPlayingMovieAdapter, AppUtils.MOVIE_NOW_PLAYING)
        })

        initLoadMore(dataBinding.includedPopularMovieLayout.moviesRecyclerView,
            dataBinding.includedPopularMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager,
            AppUtils.MOVIE_POPULAR)

        initLoadMore(dataBinding.includedNowPlayingMovieLayout.moviesRecyclerView,
            dataBinding.includedNowPlayingMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager,
            AppUtils.MOVIE_NOW_PLAYING)

        initLoadMore(dataBinding.includedUpComingMovieLayout.moviesRecyclerView,
            dataBinding.includedUpComingMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager,
            AppUtils.MOVIE_UPCOMING)

        initLoadMore(dataBinding.includedTopRateMovieLayout.moviesRecyclerView,
            dataBinding.includedTopRateMovieLayout.moviesRecyclerView.layoutManager as LinearLayoutManager,
            AppUtils.MOVIE_TOP_RATES)
    }

    private fun updateMoviesList(movies: ArrayList<MovieEntity>, adapter: MoviesAdapter, category: String) {
        if (!movies.isNullOrEmpty()) {
            adapter.setItems(movies)
        }
    }

    private fun updateGenresList(genres: List<GenresItem>) {
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
        dataBinding.includedPopularMovieLayout.moviesRecyclerView.scrollToPosition(popularListState)
        dataBinding.includedUpComingMovieLayout.moviesRecyclerView.scrollToPosition(upComingListState)
        dataBinding.includedTopRateMovieLayout.moviesRecyclerView.scrollToPosition(topRateListState)
        dataBinding.includedNowPlayingMovieLayout.moviesRecyclerView.scrollToPosition(nowPlayingListState)
    }
}

