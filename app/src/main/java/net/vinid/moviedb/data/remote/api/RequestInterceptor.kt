package net.vinid.moviedb.data.remote.api

import net.vinid.moviedb.utils.ConstStrings
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalUrl = original.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter(
                ConstStrings.QUERY_API_KEY,
                ConstStrings.TMDB_API_KEY
            )
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}