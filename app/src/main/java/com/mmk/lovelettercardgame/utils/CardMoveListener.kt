package com.mmk.lovelettercardgame.utils

import android.content.ClipData
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

class CardMoveListener :View.OnTouchListener,View.OnDragListener {
     var firstDownX=0f
    var firstDownY=0f


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

       if (event?.action==MotionEvent.ACTION_DOWN){
           val data=ClipData.newPlainText("","")
           val shadowBuilder=View.DragShadowBuilder(v)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               v?.startDragAndDrop(data,shadowBuilder,v,0)
           }
           else {
              v?.startDrag(data,shadowBuilder,v,0)
           }

           return true

       }
        return false
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
       when(event?.action){
           DragEvent.ACTION_DROP ->{

               v?.setBackgroundColor(Color.BLUE)
               println("Drag called")
               return true
           }



       }
        return true
    }
}