package net.vinid.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.mapper.toGenreItem
import net.vinid.moviedb.data.mapper.toMovieItem
import net.vinid.moviedb.data.model.GenreItem
import net.vinid.moviedb.data.model.MovieItem
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.ui.base.BaseViewModel
import net.vinid.moviedb.ui.common.EventWrapper
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_NOW_PLAYING
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_POPULAR
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_TOP_RATES
import net.vinid.moviedb.utils.ConstStrings.Companion.MOVIE_UPCOMING
import javax.inject.Inject

/**
 * Created by Nguyen Van Lieu on 2/4/2020.
 */
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel() {

    private val _popularMovie = MutableLiveData<ArrayList<MovieItem>>()
    val popularMovie: LiveData<ArrayList<MovieItem>> get() = _popularMovie
    val popularMovieVisible = Transformations.map(_popularMovie){
        it.isNotEmpty()
    }

    private val _nowPlayingMovie = MutableLiveData<ArrayList<MovieItem>>()
    val nowPlayingMovie: LiveData<ArrayList<MovieItem>> get() = _nowPlayingMovie
    val nowPlayingMovieVisible = Transformations.map(_nowPlayingMovie) {
        it.isNotEmpty()
    }

    private val _topRates = MutableLiveData<ArrayList<MovieItem>>()
    val topRatesMovie: LiveData<ArrayList<MovieItem>> get() = _topRates
    val topRatesMovieVisible = Transformations.map(_topRates) {
        it.isNotEmpty()
    }

    private val _upComing = MutableLiveData<ArrayList<MovieItem>>()
    val upComingMovie: LiveData<ArrayList<MovieItem>> get() = _upComing
    val upComingMovieVisible = Transformations.map(_upComing) {
        it.isNotEmpty()
    }

    private val _genres = MutableLiveData<ArrayList<GenreItem>>()
    val genres: LiveData<ArrayList<GenreItem>> get() = _genres

    private val _errGetData = MutableLiveData<EventWrapper<Throwable>>()
    val errorGetData: LiveData<EventWrapper<Throwable>> get() = _errGetData



    fun requestGetMovieByPage(category: String, page: Int) {
        when (category) {
            MOVIE_POPULAR -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _popularMovie.value = ArrayList() }
                        .take(1)
                        .map {
                            it.data?.toMovieItem()
                        }
                        .subscribe({
                            _popularMovie.value = it as ArrayList<MovieItem>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })
                )
            }

            MOVIE_TOP_RATES -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _topRates.value = ArrayList() }
                        .take(1)
                        .map {
                            it.data?.toMovieItem()
                        }
                        .subscribe({
                            _topRates.value = it as ArrayList<MovieItem>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })

                )
            }

            MOVIE_UPCOMING -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _upComing.value = ArrayList() }
                        .take(1)
                        .map {
                            it.data?.toMovieItem()
                        }
                        .subscribe({
                            _upComing.value = it as ArrayList<MovieItem>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })
                )
            }

            MOVIE_NOW_PLAYING -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _nowPlayingMovie.value = ArrayList() }
                        .take(1)
                        .map {
                            it.data?.toMovieItem()
                        }
                        .subscribe({
                            _nowPlayingMovie.value = it as ArrayList<MovieItem>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })
                )
            }
        }
    }

    fun requestGetListGenres(){
        addToDisposable(
            movieRepository.getListGenres()
                .filter { v -> !v.data!!.isEmpty() }
                .switchIfEmpty { _genres.value = ArrayList() }
                .take(1)
                .map {
                    it.data?.toGenreItem()
                }
                .subscribe({
                    _genres.value = it as ArrayList<GenreItem>

                }, {
                    _errGetData.value = EventWrapper(it)
                })
        )
    }

    fun requestUpdateMovieStatus(movie: MovieEntity, isLike: Boolean){
        movieRepository.updateMovieStatus(movie, isLike)
    }
}