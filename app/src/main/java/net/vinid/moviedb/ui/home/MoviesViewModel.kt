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
                title = "Ad Astra",
                vote_average = 6f,
                poster_path = "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Ford v Ferrari",
                vote_average = 7f,
                poster_path = "https://image.tmdb.org/t/p/w500/6ApDtO7xaWAfPqfi2IARXIzj8QS.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Bad Boys for Life",
                vote_average = 6f,
                poster_path = "https://image.tmdb.org/t/p/w500/y95lQLnuNKdPAzw9F9Ab8kJ80c3.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "1917",
                vote_average = 8f,
                poster_path = "https://image.tmdb.org/t/p/w500/iZf0KyrE25z1sage4SYFLCCrMi9.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Joker",
                vote_average = 8.3f,
                poster_path = "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Terminator: Dark Fate",
                vote_average = 6.2f,
                poster_path = "https://image.tmdb.org/t/p/w500/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Ip Man 4: The Finale",
                vote_average = 6f,
                poster_path = "https://image.tmdb.org/t/p/w500/yJdeWaVXa2se9agI6B4mQunVYkB.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Doctor Sleep",
                vote_average = 7.1f,
                poster_path = "https://image.tmdb.org/t/p/w500/p69QzIBbN06aTYqRRiCOY1emNBh.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Parasite",
                vote_average = 8.6f,
                poster_path = "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
                isFavorite = false
            ),
            MoviesItem(
                title = "Birds of Prey (and the Fantabulous Emancipation of One Harley Quinn)",
                vote_average = 8.3f,
                poster_path = "https://image.tmdb.org/t/p/w500/z7FCF54Jvzv9Anxyf82QeqFXXOO.jpg",
                isFavorite = false
            )
        )
        _movies.value = dataList
    }
}