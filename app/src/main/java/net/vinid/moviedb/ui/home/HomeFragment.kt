package net.vinid.moviedb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.vinid.moviedb.R
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.databinding.FragmentHomeBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.util.AppUtils

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class HomeFragment : BaseFragment() {

    private val TAG = "HomeFragment"
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
        requestGetMovie()

        return dataBinding.root
    }

    private fun requestGetMovie() {
        moviesViewModel.requestGetMovieByPage(1)
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
            Log.d(TAG,"popularMovie.observe "+it.size)
            updateMoviesList(it, popularMovieAdapter, AppUtils.MOVIE_POPULAR)
        })

        moviesViewModel.upComingMovie.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"upComingMovie.observe "+it.size)
            updateMoviesList(it, upComingMovieAdapter, AppUtils.MOVIE_UPCOMING)
        })

        moviesViewModel.topRatesMovie.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"topRatesMovie.observe "+it.size)
            updateMoviesList(it, topRateMovieAdapter, AppUtils.MOVIE_TOP_RATES)
        })

        moviesViewModel.nowPlayingMovie.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"nowPlayingMovie.observe "+it.size)
            updateMoviesList(it, nowPlayingMovieAdapter, AppUtils.MOVIE_NOW_PLAYING)
        })


        // handle error
        moviesViewModel.errorPopular.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"errorPopular.observe ")
            setVisibleNoDataTxt(View.VISIBLE, AppUtils.MOVIE_POPULAR)
        })

        moviesViewModel.errUpComing.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"upComingMovie.observe ")
            setVisibleNoDataTxt(View.VISIBLE, AppUtils.MOVIE_UPCOMING)
        })

        moviesViewModel.errTopRates.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"errorTopRate.observe ")
            setVisibleNoDataTxt(View.VISIBLE, AppUtils.MOVIE_TOP_RATES)
        })

        moviesViewModel.errNowPlaying.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"nowPlayingMovie.observe ")
            setVisibleNoDataTxt(View.VISIBLE, AppUtils.MOVIE_NOW_PLAYING)
        })
//        genresViewModel.genres.observe(viewLifecycleOwner, Observer {
//            updateGenresList(it)
//        })
    }

    private fun setVisibleNoDataTxt(visible: Int, category: String){
        when (category){
            AppUtils.MOVIE_TOP_RATES -> {
                dataBinding.includedTopRateMovieLayout.noDataTextView.visibility = visible
            }

            AppUtils.MOVIE_UPCOMING -> {
                dataBinding.includedUpComingMovieLayout.noDataTextView.visibility = visible
            }

            AppUtils.MOVIE_NOW_PLAYING -> {
                dataBinding.includedNowPlayingMovieLayout.noDataTextView.visibility = visible
            }

            AppUtils.MOVIE_POPULAR -> {
                dataBinding.includedPopularMovieLayout.noDataTextView.visibility = visible
            }
        }
    }

    private fun updateMoviesList(movies: ArrayList<MovieEntity>, adapter: MoviesAdapter, category: String) {
        if (!movies.isNullOrEmpty()) {
            adapter.setItems(movies)
            setVisibleNoDataTxt(View.GONE, category)
        } else
            setVisibleNoDataTxt(View.VISIBLE, category)
    }

    private fun updateGenresList(genres: List<GenresItem>) {
        genresAdapter.setItems(genres)
    }
}

