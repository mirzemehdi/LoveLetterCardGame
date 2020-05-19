package com.mmk.lovelettercardgame.utils.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.dialogs.discardedcards.DiscardedCardsDialog
import com.mmk.lovelettercardgame.utils.getGameCardResourceId

object CardsHolder {

    fun addCard(cardsHolderView: FrameLayout, cardType: Int, context: Context?) {
        val cardImage = initCardImage(cardType, context)
        val translationXValueBase =
            context!!.resources.getDimensionPixelSize(R.dimen.card_translation_x_all_cards_frame_layout)
        val translationYValueBase =
            context.resources.getDimensionPixelSize(R.dimen.card_translation_y_all_cards_frame_layout)

        val cardsCount = cardsHolderView.childCount
        val translationX = (cardsCount % 4) * translationXValueBase
        val translationY = if (cardsCount >= 4) translationYValueBase else 0

        cardImage.translationX = translationX.toFloat()
        cardImage.translationY = translationY.toFloat()

        cardsHolderView.addView(cardImage)
        cardsHolderView.setOnClickListener {
            cardsHolderClicked(cardsHolderView, context)
        }
    }

    fun setCardList(cardsHolderView: FrameLayout, cardTypes: List<Int>, context: Context?) {
        val currentCardsCount = cardsHolderView.childCount
        if (cardTypes.size<=currentCardsCount) return
        cardsHolderView.removeAllViews()

        cardTypes.forEach {
            addCard(cardsHolderView, it, context)
        }
        if (cardTypes.size > currentCardsCount) {
            val cardImage = cardsHolderView[currentCardsCount] as ImageView
            val zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom)
            cardImage.startAnimation(zoomAnimation)

        }
    }


    private fun cardsHolderClicked(
        cardsHolderView: FrameLayout,
        context: Context
    ) {
        val imageDrawableList = mutableListOf<Drawable?>()
        for (i in 0 until cardsHolderView.childCount) {
            val view = cardsHolderView[i] as ImageView
            imageDrawableList.add(view.drawable)
        }

        DiscardedCardsDialog(context, imageDrawableList).show()
    }

    private fun initCardImage(cardType: Int, context: Context?): ImageView {

        val cardImage = ImageView(context)
        val cardResourceId = context?.getGameCardResourceId(cardType) ?: R.drawable.card_back
        cardImage.setImageResource(cardResourceId)


        val layoutParams = LinearLayout.LayoutParams(
            context!!.resources.getDimensionPixelSize(R.dimen.card_width_all_cards_frame_layout),
            context.resources.getDimensionPixelSize(R.dimen.card_width_all_cards_frame_layout)
        )
        cardImage.layoutParams = layoutParams

        return cardImage
    }


}