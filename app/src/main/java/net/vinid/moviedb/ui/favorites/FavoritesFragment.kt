package net.vinid.moviedb.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.vinid.moviedb.MainActivity
import net.vinid.moviedb.MovieApplication
import net.vinid.moviedb.R
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.databinding.FragmentFavoritesBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.home.MoviesAdapter
import net.vinid.moviedb.ui.home.MoviesViewModel

/**
 * Created by Nguyen Van Lieu on 2/1/2020.
 */
class FavoritesFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentFavoritesBinding
    private var favoriteMovieAdapter: MoviesAdapter? = null
    private val viewModelFactory = MovieApplication.injectViewModelFactory()
    private val moviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        dataBinding.lifecycleOwner = this

        initToolbar()
        initViewModel()

        return dataBinding.root
    }

    private fun initViewModel() {
        moviesViewModel.requestGetListMoviesLiked()

        moviesViewModel.listMoviesLiked.observe(viewLifecycleOwner, Observer {
            updateListMovie(it)
        })

        favoriteMovieAdapter = MoviesAdapter()
        dataBinding.favoriteMovieRcv.adapter = favoriteMovieAdapter
    }

    private fun updateListMovie(listMovie: ArrayList<MovieItem>) {
        favoriteMovieAdapter?.setItems(listMovie)
    }

    private fun initToolbar() {
        (activity as MainActivity).setSupportActionBar(dataBinding.toolbar)
        dataBinding.toolbar.title = getString(R.string.title_favorites)
    }
}