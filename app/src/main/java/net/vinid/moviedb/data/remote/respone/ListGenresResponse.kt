package net.vinid.moviedb.data.remote.respone

import com.google.gson.annotations.SerializedName

data class ListGenresResponse (
    @SerializedName("genres") val listGenres : List<GenreResponse>
)