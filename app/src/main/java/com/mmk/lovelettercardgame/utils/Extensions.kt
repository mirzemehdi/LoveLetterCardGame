package com.mmk.lovelettercardgame.utils

import android.content.Context
import android.graphics.Point
import android.util.TypedValue
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


fun Context.toasty(text: CharSequence,
                   type:Constants.MessageType= Constants.MessageType.TYPE_NORMAL,
                   duration: Int=Toasty.LENGTH_SHORT){
    when(type){
        Constants.MessageType.TYPE_NORMAL ->  Toasty.normal(this,text,duration).show()
        Constants.MessageType.TYPE_ERROR ->  Toasty.error(this,text,duration).show()
        Constants.MessageType.TYPE_SUCCESS ->  Toasty.success(this,text,duration).show()
        Constants.MessageType.TYPE_WARNING ->  Toasty.warning(this,text,duration).show()
    }
}

fun Context.dpToPx(dp:Float):Float{
    val r=resources
    val px=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.displayMetrics)
    return px
}

fun View.getLocationOnScreen(): Point{
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Point(location[0],location[1])
}