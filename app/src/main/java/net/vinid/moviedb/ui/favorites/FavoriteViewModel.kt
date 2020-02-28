package net.vinid.moviedb.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.mapper.toMovieItem
import net.vinid.moviedb.ui.base.BaseViewModel
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) : BaseViewModel() {
    private val _listMoviesLiked = MutableLiveData<ArrayList<MovieItem>>()
    val listMoviesLiked: LiveData<ArrayList<MovieItem>> get() = _listMoviesLiked

    fun requestGetListMoviesLiked() {
        addToDisposable(
            movieRepository.getMoviesLiked()
                .map { it.data?.toMovieItem() }
                .subscribe({
                    _listMoviesLiked.value = it as ArrayList<MovieItem>
                })
        )
    }
}