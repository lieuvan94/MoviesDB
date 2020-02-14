package net.vinid.moviedb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.ui.base.BaseViewModel

/**
 * Created by Nguyen Van Lieu on 2/13/2020.
 */
class SearchMoviesViewModel : BaseViewModel() {

    private val _searchMovies = MutableLiveData<ArrayList<MovieEntity>>()
    val searchMovies: LiveData<ArrayList<MovieEntity>>
        get() = _searchMovies

    private val _errSearch = MutableLiveData<Throwable>()
    val errSearch: LiveData<Throwable>
        get() = _errSearch

    fun requestSearchMovieByPage(query: String, page: Int) {

    }
}