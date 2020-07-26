package com.example.cleanarch.core.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import java.io.File


const val NO_HOLDER = 0

fun ImageView.load(imageURL: String) {
    Picasso.get().load(imageURL).into(this)
}

fun ImageView.load(imageURL: String, @DrawableRes placeHolder: Int) {
    Picasso.get().load(imageURL).placeholder(placeHolder).into(this)
}

fun ImageView.load(@DrawableRes drawable: Int, @DrawableRes placeHolder: Int) {
    Picasso.get().load(drawable).placeholder(placeHolder).into(this)
}

fun ImageView.load(file: File, @DrawableRes placeHolder: Int) {
    Picasso.get().load(file).placeholder(placeHolder).into(this)
}

