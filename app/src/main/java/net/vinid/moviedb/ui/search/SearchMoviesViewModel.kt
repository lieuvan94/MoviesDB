package net.vinid.moviedb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.reactivex.Observable
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.data.mapper.toMovieItem
import net.vinid.moviedb.ui.base.BaseViewModel
import net.vinid.moviedb.utils.rx.SchedulerProvider
import javax.inject.Inject

/**
 * Created by Nguyen Van Lieu on 2/13/2020.
 */
class SearchMoviesViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val movieRepository: MovieRepository
) : BaseViewModel() {

    private val _searchMovies = MutableLiveData<List<MovieItem>>()
    val searchMovies: LiveData<List<MovieItem>>
        get() = _searchMovies

    val listMoviesVisible = Transformations.map(_searchMovies) {
        it.isNotEmpty()
    }

    private val _errSearchMovies = MutableLiveData<Throwable>()
    val errSearchMovies: LiveData<Throwable> get() = _errSearchMovies

    fun requestSearchMovieByPage(query: String, page: Int) {
        addToDisposable(
                Observable.just(query)
                .flatMap{ 
                    if(it.isEmpty()) Observable.just( emptyList())
                    else movieRepository.searchMoviesByQuery(it, page)
                }.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        _searchMovies.value = it.toMovieItem()
                    },{
                        _errSearchMovies.value =it
                    })
        )

    }
}
