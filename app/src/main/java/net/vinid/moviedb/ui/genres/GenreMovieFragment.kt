package net.vinid.moviedb.ui.genres


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.vinid.moviedb.MainActivity
import net.vinid.moviedb.R
import net.vinid.moviedb.data.model.GenreItem
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.databinding.FragmentGenreMovieBinding
import net.vinid.moviedb.ui.base.BaseFragment
import net.vinid.moviedb.ui.common.recycleview.EndlessRecyclerViewScrollListener
import net.vinid.moviedb.ui.home.MoviesAdapter
import net.vinid.moviedb.utils.ConstStrings.Companion.BUNDLE_KEY_GENRE_ITEM
import javax.inject.Inject


class GenreMovieFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentGenreMovieBinding
    private var genre: GenreItem? = null
    private var genreMovieAdapter: MoviesAdapter? = null

    @Inject
    lateinit var genreViewModel: GenresViewModel

    private val firstPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_genre_movie, container, false)
        dataBinding.lifecycleOwner = this

        initGenre()
        initToolbar()
        initViewModel()
        initLoadMore()

        return dataBinding.root
    }

    private fun initLoadMore() {
        genreViewModel.requestMovieByGenre(firstPage, genre!!.genreEntity.id)

        val scrollListener = object : EndlessRecyclerViewScrollListener(dataBinding.genreMovieRcv.layoutManager
                as GridLayoutManager){
            override fun setLastPosition(view: RecyclerView) {
                view.scrollToPosition(dataBinding.genreMovieRcv.adapter?.itemCount!!)
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                genreViewModel.requestMovieByGenre(page, genre!!.genreEntity.id)
            }

        }

        dataBinding.genreMovieRcv.addOnScrollListener(scrollListener)
    }

    private fun initGenre(){
        arguments?.let {
            genre = it.getSerializable(BUNDLE_KEY_GENRE_ITEM) as GenreItem?
        }
    }

    private fun initToolbar() {
        (activity as MainActivity).setSupportActionBar(dataBinding.toolbar)
        dataBinding.toolbar.title = genre!!.genreEntity.name

    }

    private fun initViewModel() {

        genreViewModel.genreListMovie.observe(viewLifecycleOwner, Observer {
            updateListMovie(it)
        })

        genreMovieAdapter = MoviesAdapter()
        dataBinding.genreMovieRcv.adapter = genreMovieAdapter


    }

    private fun updateListMovie(listMovie: ArrayList<MovieItem>) {
        genreMovieAdapter?.setItems(listMovie)
    }


}
