package net.vinid.moviedb.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.mapper.toMovieItem
import net.vinid.moviedb.ui.base.BaseViewModel
import net.vinid.moviedb.ui.common.EventWrapper
import javax.inject.Inject

class GenreViewModel @Inject constructor(private val movieRepository: MovieRepository) : BaseViewModel() {
    private val _genreListMovie = MutableLiveData<ArrayList<MovieItem>>()
    val genreListMovie: LiveData<ArrayList<MovieItem>> get() = _genreListMovie

    private val _errGetData = MutableLiveData<EventWrapper<Throwable>>()
    val errorGetData: LiveData<EventWrapper<Throwable>> get() = _errGetData

    fun requestMovieByGenre(page: Int, genreId: Int){
        addToDisposable(
            movieRepository.getMovieByGenres(page, genreId)
                .take(1)
                .map {
                    it.data?.toMovieItem()
                }
                .subscribe({
                    _genreListMovie.value = it as ArrayList<MovieItem>
                }, {
                    _errGetData.value = EventWrapper(it)
                })
        )
    }
}