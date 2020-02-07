package net.vinid.moviedb.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

/**
 * Created by Nguyen Van Lieu on 2/3/2020.
 */
data class MoviesItem(
    val title : String,
    val voteAverage : Float,
    val posterPath : String,
    var isFavorite: Boolean         //Favorite Status
): BaseObservable(){

    @get:Bindable
    var favoriteStatus: Boolean? = isFavorite
        set(value) {
            field = value
            notifyPropertyChanged(BR.favoriteStatus)
        }

    fun changeFavoriteStatus() {
        favoriteStatus = favoriteStatus?.not()
    }

    fun isFavoriteVisible() = favoriteStatus != null

}