package com.task.noteapp.utils

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.task.noteapp.R

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */

fun EditText.getContent() = text.toString().trim()

fun View.show(shouldShow: Boolean = true) {
    visibility = if (shouldShow) View.VISIBLE else View.GONE
}

fun View.gone() {
    visibility = View.GONE
}

fun ImageView.setGlideResource(content: String) {
    Glide.with(this).load(content).transform(CenterCrop(), RoundedCorners(20))
        .placeholder(R.drawable.image_placeholder).into(this)
}
