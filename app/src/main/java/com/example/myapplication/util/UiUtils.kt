package com.example.myapplication.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(uri: String?) {
		val options = RequestOptions()
		Glide.with(this.context)
			.setDefaultRequestOptions(options)
			.load(uri)
			.into(this)
	}

fun ProgressBar.show(){
	visibility = View.VISIBLE
}

fun ProgressBar.hide(){
	visibility = View.GONE
}

fun View.snackbar(message: String){
	Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->

	}.show()
}