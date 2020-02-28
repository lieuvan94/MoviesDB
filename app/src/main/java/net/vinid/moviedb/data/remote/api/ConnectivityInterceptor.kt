package net.vinid.moviedb.data.remote.api

import android.content.Context
import net.vinid.moviedb.utils.ConstStrings.Companion.NETWORK_ERR_MES
import net.vinid.moviedb.utils.NetworkManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkManager(context).isAvailable){
            throw NoConnectivityException()
        }

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    class NoConnectivityException : IOException() {
        override val message: String get() = NETWORK_ERR_MES
    }
}