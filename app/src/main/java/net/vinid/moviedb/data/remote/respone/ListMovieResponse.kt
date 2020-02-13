package net.vinid.moviedb.data.remote.respone

import com.google.gson.annotations.SerializedName

data class ListMovieResponse (
    @SerializedName("page") val page : Int,
    @SerializedName("results") val results : List<MovieResponse>,
    @SerializedName("total_results") val totalResults : Int,
    @SerializedName("total_pages") val totalPages : Int
)