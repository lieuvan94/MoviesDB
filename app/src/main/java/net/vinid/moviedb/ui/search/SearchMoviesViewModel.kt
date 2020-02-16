package net.vinid.moviedb.ui.search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.ui.base.BaseViewModel

/**
 * Created by Nguyen Van Lieu on 2/13/2020.
 */
class SearchMoviesViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _searchMovies = MutableLiveData<List<MovieEntity>>()
    val searchMovies: LiveData<List<MovieEntity>>
        get() = _searchMovies

    val listMoviesVisible = Transformations.map(_searchMovies) {
        it.isNotEmpty()
    }

    @SuppressLint("CheckResult")
    fun requestSearchMovieByPage(query: String, page: Int) {
        movieRepository.searchMoviesByQuery(query,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchMovies.postValue(it)
            }, {

            })
    }
}