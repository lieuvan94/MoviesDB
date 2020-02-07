package net.vinid.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.vinid.moviedb.ui.base.BaseViewModel

/**
 * Created by Nguyen Van Lieu on 2/4/2020.
 */
class GenresViewModel : BaseViewModel(){

    private val _genres = MutableLiveData<ArrayList<GenresItem>>()
    val genres: LiveData<ArrayList<GenresItem>>
        get() = _genres

    init {
        dummyData()
    }

    private fun dummyData(){
        val dataList = arrayListOf(
            GenresItem(
                28,
                "Action"
            ),
            GenresItem(
                12,
                "Adventure"
            ),
            GenresItem(
                16,
                "Animation"
            ),
            GenresItem(
                35,
                "Comedy"
            ),
            GenresItem(
                80,
                "Crime"
            ),
            GenresItem(
                99,
                "Documentary"
            ),
            GenresItem(
                18,
                "Drama"
            )
        )
        _genres.value = dataList
    }
}