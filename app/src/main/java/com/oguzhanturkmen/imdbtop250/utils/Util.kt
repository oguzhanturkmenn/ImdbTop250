package com.oguzhanturkmen.imdbtop250.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oguzhanturkmen.imdbtop250.R

fun ImageView.downloadFromUrl(url:String?,progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeHolderProgressBar(context:Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}
@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url:String?){
    url?.let { view.downloadFromUrl(it, placeHolderProgressBar(view.context)) }
}

@BindingAdapter("android:favoriteImage")
fun favoriteImage(view: ImageView,value:Boolean){
    if (value){
        view.setImageResource(R.drawable.favorite_button)
    }else{
        view.setImageResource(R.drawable.unfavorite_button)
    }
}