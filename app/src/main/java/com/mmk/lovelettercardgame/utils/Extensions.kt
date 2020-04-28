package com.mmk.lovelettercardgame.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import es.dmoral.toasty.Toasty

fun ViewGroup.inflate(resourceId: Int):View{
    return LayoutInflater.from(context).inflate(resourceId,this,false)
}

fun Context.toast(text:CharSequence,duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,text,duration).show()
}

fun Context.toastError(text:CharSequence,duration:Int=Toast.LENGTH_SHORT){
    Toasty.error(this,text,duration).show()
}