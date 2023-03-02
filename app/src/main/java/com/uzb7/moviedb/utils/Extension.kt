package com.uzb7.moviedb.utils

import android.view.View

object Extension {

    fun View.show(){
        this.visibility=View.VISIBLE
    }
    fun View.hide(){
        this.visibility=View.GONE
    }

}