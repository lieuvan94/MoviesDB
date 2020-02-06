package net.vinid.moviedb.util.ext

import android.content.Context
import android.net.ConnectivityManager

object AppUtils{
    const val CATEGORY_POPULAR = 1
    const val CATEGORY_NOW_PLAYING = 2
    const val CATEGORY_UPCOMING = 3
    const val CATEGORY_TOP_RATES = 4

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}

