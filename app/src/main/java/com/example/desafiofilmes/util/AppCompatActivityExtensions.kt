package com.example.desafiofilmes.util

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