package net.vinid.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import net.vinid.moviedb.data.local.entity.GenreEntity
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.ui.base.BaseViewModel
import net.vinid.moviedb.ui.common.EventWrapper
import net.vinid.moviedb.util.AppUtils

/**
 * Created by Nguyen Van Lieu on 2/4/2020.
 */
class MoviesViewModel (private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _popularMovie = MutableLiveData<ArrayList<MovieEntity>>()
    val popularMovie: LiveData<ArrayList<MovieEntity>> get() = _popularMovie
    val popularMovieVisible = Transformations.map(_popularMovie){
        it.isNotEmpty()
    }

    private val _nowPlayingMovie = MutableLiveData<ArrayList<MovieEntity>>()
    val nowPlayingMovie: LiveData<ArrayList<MovieEntity>> get() = _nowPlayingMovie
    val nowPlayingMovieVisible = Transformations.map(_nowPlayingMovie) {
        it.isNotEmpty()
    }

    private val _topRates = MutableLiveData<ArrayList<MovieEntity>>()
    val topRatesMovie: LiveData<ArrayList<MovieEntity>> get() = _topRates
    val topRatesMovieVisible = Transformations.map(_topRates) {
        it.isNotEmpty()
    }

    private val _upComing = MutableLiveData<ArrayList<MovieEntity>>()
    val upComingMovie: LiveData<ArrayList<MovieEntity>> get() = _upComing
    val upComingMovieVisible = Transformations.map(_upComing) {
        it.isNotEmpty()
    }

    private val _genres = MutableLiveData<ArrayList<GenreEntity>>()
    val genres: LiveData<ArrayList<GenreEntity>> get() = _genres

    private val _errGetData = MutableLiveData<EventWrapper<Throwable>>()
    val errorGetData: LiveData<EventWrapper<Throwable>> get() = _errGetData


    fun requestGetMovieByPage(category: String, page: Int) {
        when (category) {
            AppUtils.MOVIE_POPULAR -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _popularMovie.value = ArrayList() }
                        .take(1)
                        .subscribe({
                            _popularMovie.value = it.data!! as ArrayList<MovieEntity>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })
                )
            }

            AppUtils.MOVIE_TOP_RATES -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _topRates.value = ArrayList() }
                        .take(1)
                        .subscribe({
                            _topRates.value = it.data!! as ArrayList<MovieEntity>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })

                )
            }

            AppUtils.MOVIE_UPCOMING -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _upComing.value = ArrayList() }
                        .take(1)
                        .subscribe({
                            _upComing.value = it.data!! as ArrayList<MovieEntity>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })
                )
            }

            AppUtils.MOVIE_NOW_PLAYING -> {
                addToDisposable(
                    movieRepository.getMovieByCategory(category, page)
                        .filter { v -> !v.data!!.isEmpty() }
                        .switchIfEmpty { _nowPlayingMovie.value = ArrayList() }
                        .take(1)
                        .subscribe({
                            _nowPlayingMovie.value = it.data!! as ArrayList<MovieEntity>
                        }, {
                            _errGetData.value = EventWrapper(it)
                        })
                )
            }
        }
    }

    fun requestMovieByGenre(page: Int, genre: Int){
        addToDisposable(
            movieRepository.getMovieByGenres(page, genre)
                .filter { v -> !v.data!!.isEmpty() }
                .switchIfEmpty { _nowPlayingMovie.value = ArrayList() }
                .take(1)
                .subscribe({
                    _nowPlayingMovie.value = it.data!! as ArrayList<MovieEntity>
                }, {
                    _errGetData.value = EventWrapper(it)
                })
        )
    }

    fun requestGetListGenres(){
        addToDisposable(
            movieRepository.getListGenres()
                .filter { v -> !v.data!!.isEmpty() }
                .switchIfEmpty { _genres.value = ArrayList() }
                .take(1)
                .subscribe({
                    _genres.value = it.data!! as ArrayList<GenreEntity>
                }, {
                    _errGetData.value = EventWrapper(it)
                })
        )
    }
}