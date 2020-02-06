package net.vinid.moviedb.data.remote.api

import io.reactivex.Observable
import io.reactivex.Single
import net.vinid.moviedb.data.remote.respone.ListMovieRespone
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("movie/popular")
    fun getMoviePopular(@Query("page") page: Int): Observable<ListMovieRespone>

    @GET("movie/upcoming")
    fun getMovieUpcoming(@Query("page") page: Int): Observable<ListMovieRespone>

    @GET("movie/top_rated")
    fun getMovieTopRated(@Query("page") page: Int): Observable<ListMovieRespone>

    @GET("movie/now_playing")
    fun getMovieNowPlaying(@Query("page") page: Int): Observable<ListMovieRespone>


}