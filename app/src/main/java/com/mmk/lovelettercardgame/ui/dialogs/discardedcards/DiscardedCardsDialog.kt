package com.mmk.lovelettercardgame.ui.dialogs.discardedcards

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.mmk.lovelettercardgame.R
import kotlinx.android.synthetic.main.dialog_player_discarded_cards.view.*

class DiscardedCardsDialog(
    private val context: Context?,
    imageDrawableList: MutableList<Drawable?>

) {


    private val dialog: Dialog = Dialog(context!!)
    private val view: View


    init {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_player_discarded_cards, null)

        dialog.setContentView(view)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val gridLayoutContainer=view.grid_layout_discarded_cards
        imageDrawableList.forEach {
            val cardImageView=initCardImage(it)
            gridLayoutContainer.addView(cardImageView)
        }

    }


    fun show() {
        dialog.window?.attributes?.windowAnimations = R.style.CardInfoDialogAnimation
        dialog.show()
    }

    private fun initCardImage(cardImageDrawable:Drawable?): ImageView {

        val cardImage = ImageView(context)
        cardImage.setImageDrawable(cardImageDrawable)


        val layoutParams = LinearLayout.LayoutParams(
            context!!.resources.getDimensionPixelSize(R.dimen.card_width_discarded_cards_dialog),
            context.resources.getDimensionPixelSize(R.dimen.card_height_discarded_cards_dialog)
        )
        val padding=context.resources.getDimensionPixelSize(R.dimen.card_padding_discarded_cards_dialog)

        cardImage.setPadding(padding,padding,padding,padding)
        cardImage.layoutParams = layoutParams
        return cardImage
    }


}