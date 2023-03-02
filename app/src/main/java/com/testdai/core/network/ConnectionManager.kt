package com.testdai.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.Looper

class ConnectionManager constructor(context: Context) {

    private val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (hasInternetCapability == true) {
                validNetworks.add(network)
            }
            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
    private val validNetworks: MutableSet<Network> = HashSet()
    private var isLastConnectionAvailable = true
    private val hasNetworks: Boolean
        get() = validNetworks.isNotEmpty()

    private val handler = Handler(Looper.getMainLooper())

    private var listener: Listener? = null

    fun register(listener: Listener) {
        this.listener = listener
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun unregister() {
        this.listener = null
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun checkValidNetworks() {
        isLastConnectionAvailable = if (hasNetworks) {
            if (!isLastConnectionAvailable) {
                handler.post {
                    listener?.connected()
                }
            }
            true
        } else {
            handler.post {
                listener?.disconnected()
            }
            false
        }
    }

    interface Listener {
        fun connected()
        fun disconnected()
    }

}