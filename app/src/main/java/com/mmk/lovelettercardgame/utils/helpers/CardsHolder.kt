package com.mmk.lovelettercardgame.utils.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.dialogs.discardedcards.DiscardedCardsDialog
import com.mmk.lovelettercardgame.utils.getGameCardResourceId

object CardsHolder {

    fun addCard(cardsHolderView: FrameLayout, cardType: Int, context: Context?,lastcard:Boolean=false) {
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

        if (lastcard) {
            val transition = Slide(Gravity.TOP)
            TransitionManager.beginDelayedTransition(cardsHolderView, transition)

        }
        cardsHolderView.addView(cardImage)
        cardsHolderView.setOnClickListener {
            cardsHolderClicked(cardsHolderView, context)
        }

        if(lastcard){


            cardImage.animate().withLayer()
                .rotationY(90f)
                .setDuration(400)
                .withEndAction {
                    cardImage.rotationY=-90f
                    cardImage.animate().withLayer()
                        .rotationY(0f)
                        .setDuration(400)
                        .start()

                }
                .start()

        }
    }

    fun setCardList(cardsHolderView: FrameLayout, cardTypes: List<Int>, context: Context?) {
        val currentCardsCount = cardsHolderView.childCount

        cardsHolderView.removeAllViews()
        if(cardTypes.isEmpty()) return

        for (i in 0 until cardTypes.size-1){
            addCard(cardsHolderView, cardTypes[i], context)
        }
        if (cardTypes.size > currentCardsCount) {
            addCard(cardsHolderView, cardTypes.last(), context, true)
        }
        else
            addCard(cardsHolderView, cardTypes.last(), context)



//            val cardImage = cardsHolderView[currentCardsCount] as ImageView
//            val zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom)
//            cardImage.startAnimation(zoomAnimation)

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