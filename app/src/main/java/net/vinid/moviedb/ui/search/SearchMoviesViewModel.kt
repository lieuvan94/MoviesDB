package net.vinid.moviedb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.mapper.toMovieItem
import net.vinid.moviedb.ui.base.BaseViewModel

/**
 * Created by Nguyen Van Lieu on 2/13/2020.
 */
class SearchMoviesViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _searchMovies = MutableLiveData<List<MovieItem>>()
    val searchMovies: LiveData<List<MovieItem>>
        get() = _searchMovies

    val listMoviesVisible = Transformations.map(_searchMovies) {
        it.isNotEmpty()
    }

    private val _errSearchMovies = MutableLiveData<Throwable>()
    val errSearchMovies: LiveData<Throwable> get() = _errSearchMovies

    fun requestSearchMovieByPage(query: String, page: Int) {
        if(query.isEmpty()){
            _searchMovies.value = ArrayList()
        }else{
            addToDisposable(
                movieRepository.searchMoviesByQuery(query,page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _searchMovies.value = it.toMovieItem()
                    },{
                        _errSearchMovies.value =it
                    })
            )
        }

    }
}