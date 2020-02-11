package net.vinid.moviedb.data.remote.api

import net.vinid.moviedb.util.AppUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIManager {
    private var retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 10
    private var okHttpClient: OkHttpClient? = null

    fun getClient(): Retrofit? {
        if (okHttpClient == null) initOkHttp()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(AppUtils.BASE_MOVIE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalUrl =original.url()
            val url =originalUrl.newBuilder()
                .addQueryParameter(AppUtils.QUERY_API_KEY,
                    AppUtils.TMDB_API_KEY
                )
                .build()

            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        okHttpClient = httpClient.build()
    }
}