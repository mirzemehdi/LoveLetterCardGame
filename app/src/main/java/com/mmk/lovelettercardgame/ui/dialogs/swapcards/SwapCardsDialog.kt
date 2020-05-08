package com.mmk.lovelettercardgame.ui.dialogs.swapcards

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import kotlinx.android.synthetic.main.dialog_card_info.view.*
import kotlinx.android.synthetic.main.dialog_swap_cards.view.*
import java.util.logging.Handler

class SwapCardsDialog(
    private val activity: Activity?,
    private val firstCard: ImageView,
    private val secondCard: ImageView,
    private val firstPlayerName: String = "",
    private val secondPlayerName: String = ""
) {

    private val dialog: Dialog = Dialog(activity!!)
    private val view: View
    private val firstCardImageView: ImageView
    private val secondCardImageView: ImageView


    init {
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_swap_cards, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.color.colorDialogSwapCardsBg);

        firstCardImageView = view.image_view_dialog_swap_cards_first
        secondCardImageView = view.image_view_dialog_swap_cards_second
        firstCardImageView.setImageDrawable(firstCard.drawable)
        secondCardImageView.setImageDrawable(secondCard.drawable)

        view.text_view_dialog_swap_cards_first_player_name.text=firstPlayerName
        view.text_view_dialog_swap_cards_second_player_name.text=secondPlayerName

    }

    fun show() {
        dialog.window?.attributes?.windowAnimations = R.style.CardSwapDialogAnimation
        dialog.show()
        android.os.Handler().postDelayed({

            CardAnimations.swapCards(firstCardImageView, secondCardImageView) {
                //Animation finished
                dialog.dismiss()
            }

        },300)

    }


}