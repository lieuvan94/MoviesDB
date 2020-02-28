package net.vinid.moviedb.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager(private var applicationContext: Context) {
    val isAvailable: Boolean
        get() {
            val conManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = conManager.activeNetworkInfo
            return ni != null && ni.isConnected
        }
}