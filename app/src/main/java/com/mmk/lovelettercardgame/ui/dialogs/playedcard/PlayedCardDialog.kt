package com.mmk.lovelettercardgame.ui.dialogs.playedcard

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.utils.getGameCardResourceId
import kotlinx.android.synthetic.main.dialog_played_card_dialog.view.*

class PlayedCardDialog(private val activity: Activity?, private val cardType:Int,
                       private val playerFrom:String="",private val playerTo:String="") {

    private val dialog :Dialog = Dialog(activity!!)
    private val view: View
    private val cardImageView:ImageView





    init {
        view=LayoutInflater.from(activity).inflate(R.layout.dialog_played_card_dialog,null)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        cardImageView=view.image_view_played_card
        cardImageView.setImageResource(activity?.getGameCardResourceId(cardType)?:R.drawable.card_back)
        view.text_view_played_card_from.text=playerFrom
        view.text_view_played_card_to.text=playerTo
    }

    fun show(){
        dialog.window?.attributes?.windowAnimations=R.style.CardPlayedDialogAnimation
        dialog.show()
       android.os.Handler().postDelayed({dialog.dismiss()},2500)
    }


}