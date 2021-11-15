package com.example.desafiofilmes.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

fun AppCompatActivity.glideThis(with: View, url: String?, into: ImageView){
    if(url != null){
        Glide
            .with(with)
            .load(url)
            .into(into)
    } else {
        Glide
            .with(with)
            .load("@drawable/mock_image_not_found")
            .into(into)
    }
}

fun AppCompatActivity.canToastThis(message : String){
    Toast.makeText(this.baseContext, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.checkConnection():Boolean {
    var result = false
    val connectivityManager =
        baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return result
}