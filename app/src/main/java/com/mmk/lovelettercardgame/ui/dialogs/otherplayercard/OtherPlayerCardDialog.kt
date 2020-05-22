package com.mmk.lovelettercardgame.ui.dialogs.otherplayercard

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.utils.getGameCardResourceId
import kotlinx.android.synthetic.main.dialog_other_player_card.view.*
import kotlinx.android.synthetic.main.dialog_played_card_dialog.view.*

class OtherPlayerCardDialog(private val activity: Activity?, private val cardType:Int,
                            private val playerName:String="") {

    private val dialog :Dialog = Dialog(activity!!)
    private val view: View
    private val cardImageView:ImageView





    init {
        view=LayoutInflater.from(activity).inflate(R.layout.dialog_other_player_card,null)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        cardImageView=view.image_view_dialog_other_player_card
        cardImageView.setImageResource(activity?.getGameCardResourceId(cardType)?:R.drawable.card_back)
        val otherPlayerCardText=activity?.getString(R.string.other_player_card,playerName)
        view.text_view_dialog_other_player_card_name.text=otherPlayerCardText
    }

    fun show(){
        dialog.window?.attributes?.windowAnimations=R.style.CardPlayedDialogAnimation
        dialog.show()
       android.os.Handler().postDelayed({dialog.dismiss()},2000)
    }


}