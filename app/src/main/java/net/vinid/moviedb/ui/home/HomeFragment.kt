package net.vinid.moviedb.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.vinid.moviedb.R
import net.vinid.moviedb.databinding.FragmentHomeBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.common.recycleview.*

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
    private var listMoviesItem = ArrayList<MoviesItem>()
    private var listGenresItem = ArrayList<GenresItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        dataBinding.lifecycleOwner = this

        handelUpdateData()

        return dataBinding.root
    }

    private fun handelUpdateData() {
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer {
            listMoviesItem = it
            setDataBindingMoviesRecycleView(listMoviesItem,dataBinding.popularMoviesRecyclerView,5)
            setDataBindingMoviesRecycleView(listMoviesItem,dataBinding.nowPlayingMoviesRecyclerView,5)
            setDataBindingMoviesRecycleView(listMoviesItem,dataBinding.upComingMoviesRecyclerView,5)
            setDataBindingMoviesRecycleView(listMoviesItem,dataBinding.topRatesMoviesRecyclerView,5)

        })

        genresViewModel.genres.observe(viewLifecycleOwner, Observer {
            listGenresItem = it
            setDataBindingGenresRecycleView(listGenresItem,dataBinding.genresRecyclerView,1)
        })
    }

    private fun setDataBindingMoviesRecycleView(listMovies : ArrayList<MoviesItem>, recycleView : RecyclerView, space : Int) {
        moviesAdapter = MoviesAdapter(listMovies)
        val itemDecoration = ItemOffsetDecoration(space)
        recycleView.apply {
            addItemDecoration(itemDecoration)
            layoutManager = context?.let { LinearLayoutManager(it) }
            adapter = this@HomeFragment.moviesAdapter
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.layoutManager = layoutManager

            moviesAdapter.onItemClick ={
                Toast.makeText(context,"Movies name: "+ it.title,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setDataBindingGenresRecycleView(listGenres : ArrayList<GenresItem>, recycleView : RecyclerView, space : Int) {
        genresAdapter = GenresAdapter(listGenres)
        val itemDecoration = ItemOffsetDecoration(space)
        recycleView.apply {
            addItemDecoration(itemDecoration)
            layoutManager = context?.let { LinearLayoutManager(it) }
            adapter = this@HomeFragment.genresAdapter
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.layoutManager = layoutManager

            genresAdapter.onItemClick ={
                Toast.makeText(context,"Genres name: "+ it.name,Toast.LENGTH_LONG).show()
            }
        }
    }
}