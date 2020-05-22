package com.mmk.lovelettercardgame.utils

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.CardPojo
import es.dmoral.toasty.Toasty

fun ViewGroup.inflate(resourceId: Int): View {
    return LayoutInflater.from(context).inflate(resourceId, this, false)
}

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}


fun Context.toasty(
    text: CharSequence,
    type: Constants.MessageType = Constants.MessageType.TYPE_NORMAL,
    duration: Int = Toasty.LENGTH_SHORT
) {
    when (type) {
        Constants.MessageType.TYPE_NORMAL -> Toasty.normal(this, text, duration).show()
        Constants.MessageType.TYPE_ERROR -> Toasty.error(this, text, duration).show()
        Constants.MessageType.TYPE_SUCCESS -> Toasty.success(this, text, duration).show()
        Constants.MessageType.TYPE_WARNING -> Toasty.warning(this, text, duration).show()
    }
}

fun Context.dpToPx(dp: Float): Float {
    val r = resources
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics)
    return px
}

fun View.getLocationOnScreen(): Point {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Point(location[0], location[1])
}

fun Context.getGameCardResourceId(type: Int): Int {
    return when (type) {
        CardPojo.TYPE_GUARD -> R.drawable.card_guard
        CardPojo.TYPE_PRIEST -> R.drawable.card_priest
        CardPojo.TYPE_BARON -> R.drawable.card_baron
        CardPojo.TYPE_HANDMAID -> R.drawable.card_handmaid
        CardPojo.TYPE_PRINCE -> R.drawable.card_prince
        CardPojo.TYPE_KING -> R.drawable.card_king
        CardPojo.TYPE_COUNTESS -> R.drawable.card_countess
        CardPojo.TYPE_PRINCESS -> R.drawable.card_princess
        else -> R.drawable.card_back
    }

}