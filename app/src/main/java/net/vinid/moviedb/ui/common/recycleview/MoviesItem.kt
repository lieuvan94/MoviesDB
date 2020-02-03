package net.vinid.moviedb.ui.common.recycleview

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

/**
 * Created by Nguyen Van Lieu on 2/3/2020.
 */
data class MoviesItem(
    val title : String,
    val vote_average : Float,
    val poster_path : String,
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