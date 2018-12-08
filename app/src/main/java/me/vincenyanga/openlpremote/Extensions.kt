package me.vincenyanga.openlpremote

import android.view.View

fun View.setIsVisible(isVisible: Boolean){
    if (isVisible){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}