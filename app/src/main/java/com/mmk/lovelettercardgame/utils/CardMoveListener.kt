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
import com.mmk.lovelettercardgame.pojo.CardPojo

class CardMoveListener(private val onCardPlayed: ((cardPOJO: CardPojo, targetPlayerId: String?) -> Unit)?) :
    View.OnLongClickListener, View.OnDragListener {


//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//
//       if (event?.action==MotionEvent.ACTION_DOWN){
//
//
//           return true
//
//       }
//        return false
//    }

    override fun onLongClick(v: View?): Boolean {

        val data = ClipData.newPlainText("", "")
        val shadowBuilder = View.DragShadowBuilder(v)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v?.startDragAndDrop(data, shadowBuilder, v, 0)
        } else {
            v?.startDrag(data, shadowBuilder, v, 0)
        }
        return true
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        when (event?.action) {
            DragEvent.ACTION_DROP -> {
                val cardView = event.localState as View
                if (cardView != v) {
                    val cardPOJO=cardView.tag as CardPojo
                    println("Played Card: $cardPOJO")
                    val targetPlayerId=v?.tag as String?
                    v?.setBackgroundColor(Color.BLUE) //TODO REMOVE THIS LINE

                    (onCardPlayed!!)(cardPOJO, targetPlayerId)


                }
                println("Drag called")
                return true
            }


        }
        return true
    }
}