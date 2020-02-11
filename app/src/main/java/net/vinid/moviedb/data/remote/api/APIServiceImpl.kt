package net.vinid.moviedb.data.remote.api

import io.reactivex.Observable
import net.vinid.moviedb.data.remote.respone.ListMovieRespone

class APIServiceImpl (
    private val apiManager: APIService
) : APIService{
    override fun getMovieByCategory(category: String, page: Int): Observable<ListMovieRespone> {
        return apiManager.getMovieByCategory(category,page)
    }
}