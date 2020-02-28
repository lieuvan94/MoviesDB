package net.vinid.moviedb.data.remote.api

import io.reactivex.Observable
import net.vinid.moviedb.data.remote.respone.ListGenresResponse
import net.vinid.moviedb.data.remote.respone.ListMovieResponse

class APIServiceImpl (
    private val apiManager: APIService
) : APIService{
    override fun getMovieByCategory(category: String, page: Int): Observable<ListMovieResponse> {
        return apiManager.getMovieByCategory(category,page)
    }

    override fun getMovieByGenres(page: Int, genre: Int
    ): Observable<ListMovieResponse> {
        return apiManager.getMovieByGenres(page, genre)
    }

    override fun getListGenres(): Observable<ListGenresResponse> {
        return apiManager.getListGenres()
    }

    override fun searchMoviesByQuery(query: String, page: Int): Observable<ListMovieResponse> {
        return apiManager.searchMoviesByQuery(query,page)
    }
}