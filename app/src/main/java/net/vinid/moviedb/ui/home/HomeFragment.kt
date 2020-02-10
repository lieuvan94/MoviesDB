package net.vinid.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentHomeBinding
import net.vinid.moviedb.ui.base.BaseFragment

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class HomeFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentHomeBinding

    private val moviesViewModel: MoviesViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesViewModel::class.java)
    }
    private val genresViewModel: GenresViewModel by lazy {
        ViewModelProviders.of(this).get(GenresViewModel::class.java)
    }

    private lateinit var moviesAdapter: MoviesAdapter
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

        return dataBinding.root
    }

    private fun initView(){
        dataBinding.includedPopularMovieLayout.movieCategoryTitle.setText(R.string.popular_movies_label)
        dataBinding.includedNowPlayingMovieLayout.movieCategoryTitle.setText(R.string.now_playing_movies_label)
        dataBinding.includedUpComingMovieLayout.movieCategoryTitle.setText(R.string.upcoming_movies_label)
        dataBinding.includedTopRateMovieLayout.movieCategoryTitle.setText(R.string.top_rates_movies_label)

        moviesAdapter = MoviesAdapter()

        dataBinding.includedPopularMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.moviesAdapter
        dataBinding.includedNowPlayingMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.moviesAdapter
        dataBinding.includedUpComingMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.moviesAdapter
        dataBinding.includedTopRateMovieLayout.moviesRecyclerView.adapter = this@HomeFragment.moviesAdapter

        genresAdapter = GenresAdapter()
        dataBinding.genresRecyclerView.adapter = this@HomeFragment.genresAdapter

    }

    private fun initViewModel() {
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer {
            updateMoviesList(it)
        })

        genresViewModel.genres.observe(viewLifecycleOwner, Observer {
            updateGenresList(it)
        })
    }

    private fun updateMoviesList(movies: List<MoviesItem>) {
        moviesAdapter.setItems(movies)
    }
    private fun updateGenresList(genress: List<GenresItem>) {
        genresAdapter.setItems(genress)
    }
}

