package net.vinid.moviedb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.vinid.moviedb.data.repository.MovieRepository
import net.vinid.moviedb.ui.home.MoviesViewModel
import net.vinid.moviedb.ui.search.SearchMoviesViewModel

class ViewModelFactory (private val repository: MovieRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(this.repository) as T
            }
            modelClass.isAssignableFrom(SearchMoviesViewModel::class.java) -> {
                SearchMoviesViewModel(this.repository) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}