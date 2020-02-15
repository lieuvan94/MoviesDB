package net.vinid.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.vinid.moviedb.MovieApplication
import net.vinid.moviedb.R
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.databinding.FragmentHomeBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.util.AppUtils

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class HomeFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentHomeBinding

    private val viewModelFactory = MovieApplication.injectViewModelFactory()

    private val moviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)
    }

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
        dataBinding.viewModel = moviesViewModel

        initView()
        initViewModel()
        requestGetMovie()

        return dataBinding.root
    }

    private fun requestGetMovie() {
        moviesViewModel.requestGetMovieByPage(1)
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

    }

    private fun initViewModel() {
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


        // handle error
        moviesViewModel.errorPopular.observe(viewLifecycleOwner, Observer {
            //Todo Show dialog display error
        })

        moviesViewModel.errUpComing.observe(viewLifecycleOwner, Observer {
            //Todo Show dialog display error
        })

        moviesViewModel.errTopRates.observe(viewLifecycleOwner, Observer {
            //Todo Show dialog display error
        })

        moviesViewModel.errNowPlaying.observe(viewLifecycleOwner, Observer {
            //Todo Show dialog display error
        })
//        genresViewModel.genres.observe(viewLifecycleOwner, Observer {
//            updateGenresList(it)
//        })
    }


    private fun updateMoviesList(movies: ArrayList<MovieEntity>, adapter: MoviesAdapter, category: String) {
        adapter.setItems(movies)
    }

    private fun updateGenresList(genres: List<GenresItem>) {
        genresAdapter.setItems(genres)
    }
}

