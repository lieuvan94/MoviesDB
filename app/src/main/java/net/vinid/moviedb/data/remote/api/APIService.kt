package net.vinid.moviedb.data.remote.api

import io.reactivex.Observable
import net.vinid.moviedb.data.remote.respone.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("movie/{category}")
    fun getMovieByCategory(@Path("category") category: String,@Query("page") page: Int)
            : Observable<ListMovieResponse>
}