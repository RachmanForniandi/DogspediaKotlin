package com.example.dogspediakotlin.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogspediakotlin.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri:String?, progressDrawable: CircularProgressDrawable){
    val requestOptions = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.placeholder)
    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(uri)
        .into(this)
}