package net.vinid.moviedb.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import net.vinid.moviedb.data.local.entity.MovieEntity

data class MovieItem (
    val movieEntity: MovieEntity,
    var favorite: Boolean
) : BaseObservable(){

    @get:Bindable
    var favoriteStatus: Boolean = favorite
        set(value) {
            field = value
            notifyPropertyChanged(BR.favoriteStatus)
        }


    fun changeFavoriteStatus() {
        favoriteStatus = favoriteStatus.not()
    }

}