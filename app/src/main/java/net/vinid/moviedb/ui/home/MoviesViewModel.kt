package net.vinid.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.vinid.moviedb.ui.base.BaseViewModel

/**
 * Created by Nguyen Van Lieu on 2/4/2020.
 */
class MoviesViewModel : BaseViewModel(){

    private val _movies = MutableLiveData<ArrayList<MoviesItem>>()
    val movies: LiveData<ArrayList<MoviesItem>>
        get() = _movies

    init {
        dummyData()
    }

    private fun dummyData(){
        val dataList = arrayListOf(
            MoviesItem(
                id = 1,
                title = "Ad Astra",
                voteAverage = 6f,
                posterPath = "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 2,
                title = "Ford v Ferrari",
                voteAverage = 7f,
                posterPath = "https://image.tmdb.org/t/p/w500/6ApDtO7xaWAfPqfi2IARXIzj8QS.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 3,
                title = "Bad Boys for Life",
                voteAverage = 6f,
                posterPath = "https://image.tmdb.org/t/p/w500/y95lQLnuNKdPAzw9F9Ab8kJ80c3.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 4,
                title = "1917",
                voteAverage = 8f,
                posterPath = "https://image.tmdb.org/t/p/w500/iZf0KyrE25z1sage4SYFLCCrMi9.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 5,
                title = "Joker",
                voteAverage = 8.3f,
                posterPath = "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 6,
                title = "Terminator: Dark Fate",
                voteAverage = 6.2f,
                posterPath = "https://image.tmdb.org/t/p/w500/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 7,
                title = "Ip Man 4: The Finale",
                voteAverage = 6f,
                posterPath = "https://image.tmdb.org/t/p/w500/yJdeWaVXa2se9agI6B4mQunVYkB.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 8,
                title = "Doctor Sleep",
                voteAverage = 7.1f,
                posterPath = "https://image.tmdb.org/t/p/w500/p69QzIBbN06aTYqRRiCOY1emNBh.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 9,
                title = "Parasite",
                voteAverage = 8.6f,
                posterPath = "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
                isFavorite = false
            ),
            MoviesItem(
                id = 10,
                title = "Birds of Prey (and the Fantabulous Emancipation of One Harley Quinn)",
                voteAverage = 8.3f,
                posterPath = "https://image.tmdb.org/t/p/w500/z7FCF54Jvzv9Anxyf82QeqFXXOO.jpg",
                isFavorite = false
            )
        )
        _movies.value = dataList
    }
}