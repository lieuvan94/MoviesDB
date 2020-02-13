package net.vinid.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.ui.base.BaseViewModel
import net.vinid.moviedb.util.AppUtils

/**
 * Created by Nguyen Van Lieu on 2/4/2020.
 */
class MoviesViewModel (private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _popularMovie = MutableLiveData<ArrayList<MovieEntity>>()
    val popularMovie: LiveData<ArrayList<MovieEntity>> get() = _popularMovie

    private val _nowPlayingMovie = MutableLiveData<ArrayList<MovieEntity>>()
    val nowPlayingMovie: LiveData<ArrayList<MovieEntity>> get() = _nowPlayingMovie

    private val _topRates = MutableLiveData<ArrayList<MovieEntity>>()
    val topRatesMovie: LiveData<ArrayList<MovieEntity>> get() = _topRates

    private val _upComing = MutableLiveData<ArrayList<MovieEntity>>()
    val upComingMovie: LiveData<ArrayList<MovieEntity>> get() = _upComing

    private val _errPopular = MutableLiveData<Throwable>()
    val errorPopular: LiveData<Throwable> get() = _errPopular

    private val _errNowPlaying = MutableLiveData<Throwable>()
    val errNowPlaying: LiveData<Throwable> get() = _errNowPlaying

    private val _errTopRates = MutableLiveData<Throwable>()
    val errTopRates: LiveData<Throwable> get() = _errTopRates

    private val _errUpComing = MutableLiveData<Throwable>()
    val errUpComing: LiveData<Throwable> get() = _errUpComing

    fun requestGetMovieByPage(page: Int) {
        addToDisposable(
            movieRepository.getMovieByCategory(AppUtils.MOVIE_POPULAR, page)
                .filter { v -> !v.data!!.isEmpty() }
                .switchIfEmpty {_popularMovie.value = ArrayList()}
                .subscribe({
                    _popularMovie.value = it.data!! as ArrayList<MovieEntity>
                }, {
                    _errPopular.value = it
                })
        )

        addToDisposable(
            movieRepository.getMovieByCategory(AppUtils.MOVIE_NOW_PLAYING, page)
                .filter { v -> !v.data!!.isEmpty() }
                .switchIfEmpty {_nowPlayingMovie.value = ArrayList()}
                .subscribe({
                    _nowPlayingMovie.value = it.data!! as ArrayList<MovieEntity>
                }, {
                    _errNowPlaying.value = it
                })
        )

        addToDisposable(
            movieRepository.getMovieByCategory(AppUtils.MOVIE_UPCOMING, page)
                .filter { v -> !v.data!!.isEmpty() }
                .switchIfEmpty {_upComing.value = ArrayList()}
                .subscribe({
                    _upComing.value = it.data!! as ArrayList<MovieEntity>
                }, {
                    _errUpComing.value = it
                })
        )

        addToDisposable(
            movieRepository.getMovieByCategory(AppUtils.MOVIE_TOP_RATES, page)
                .filter { v -> !v.data!!.isEmpty() }
                .switchIfEmpty {_topRates.value = ArrayList()}
                .subscribe({
                    _topRates.value = it.data!! as ArrayList<MovieEntity>
                }, {
                    _errTopRates.value = it
                })
        )
    }
}