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
import net.vinid.moviedb.ui.common.recycleview.ItemOffsetDecoration

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

        initViewModel()

        return dataBinding.root
    }

    private fun initViewModel() {
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer {listMoviesItem ->
            initMoviesRecycleView(listMoviesItem,dataBinding.popularMoviesRecyclerView,5)
            initMoviesRecycleView(listMoviesItem,dataBinding.nowPlayingMoviesRecyclerView,5)
            initMoviesRecycleView(listMoviesItem,dataBinding.upComingMoviesRecyclerView,5)
            initMoviesRecycleView(listMoviesItem,dataBinding.topRatesMoviesRecyclerView,5)
        })

        genresViewModel.genres.observe(viewLifecycleOwner, Observer {listGenresItem->
            initGenresRecycleView(listGenresItem,dataBinding.genresRecyclerView,1)
        })
    }

    private fun initMoviesRecycleView(listMovies : ArrayList<MoviesItem>, recycleView : RecyclerView, space : Int) {
        moviesAdapter = MoviesAdapter(listMovies)
        val itemDecoration = ItemOffsetDecoration(space)
        recycleView.apply {
            addItemDecoration(itemDecoration)
            layoutManager = context?.let { LinearLayoutManager(it) }
            adapter = this@HomeFragment.moviesAdapter
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.layoutManager = layoutManager

            moviesAdapter.onItemClick ={
                //TODO: Show movies details
            }
        }
    }

    private fun initGenresRecycleView(listGenres : ArrayList<GenresItem>, recycleView : RecyclerView, space : Int) {
        genresAdapter = GenresAdapter(listGenres)
        val itemDecoration = ItemOffsetDecoration(space)
        recycleView.apply {
            addItemDecoration(itemDecoration)
            layoutManager = context?.let { LinearLayoutManager(it) }
            adapter = this@HomeFragment.genresAdapter
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.layoutManager = layoutManager

            genresAdapter.onItemClick ={
                //TODO: Show movies details
            }
        }
    }
}