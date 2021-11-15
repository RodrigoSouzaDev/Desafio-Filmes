package com.example.desafiofilmes.core.extensions

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.expand(){
    this.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
}