package com.mmk.lovelettercardgame.utils

import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CardMoveListener :View.OnTouchListener {
     var firstDownX=0f
    var firstDownY=0f


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        event?.setLocation(event.rawX,event.rawY)

        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN ->{
                firstDownX=event.x-v!!.x
                firstDownY=event.y-v.y
            }
            MotionEvent.ACTION_MOVE ->{
                val movementX=event.x-firstDownX
                val movementY=event.y-firstDownY

                v?.x=movementX
                v?.y=movementY
            }

            MotionEvent.ACTION_BUTTON_PRESS->
                v?.performClick()
        }
        return false
    }

}