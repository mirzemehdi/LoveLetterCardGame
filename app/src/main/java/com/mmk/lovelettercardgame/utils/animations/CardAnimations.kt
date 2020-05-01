package com.mmk.lovelettercardgame.utils.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.mmk.lovelettercardgame.utils.getLocationOnScreen
import kotlinx.android.synthetic.main.fragment_game.*

class CardAnimations {
    companion object {
        const val DURATION_DEAL_CARD_ANIMATION=500L
        const val DURATION_ARRANGE_CARDS_ANIMATION=200L

        fun dealCard(isOtherPlayer:Boolean, sampleCard: View,
                     cardFinalPositionView: View, onAnimationEnd: () -> Unit={}) {

            val animationDuration= DURATION_DEAL_CARD_ANIMATION
            val scaleValue=if(isOtherPlayer) 0.5f else 4f

            sampleCard.visibility = View.VISIBLE
            sampleCard.scaleX=scaleValue
            sampleCard.scaleY=scaleValue
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, scaleValue)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, scaleValue)

            val finalYPosition = cardFinalPositionView.getLocationOnScreen().y.toFloat()-
                    sampleCard.getLocationOnScreen().y.toFloat()
            val translationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, finalYPosition)

            val finalXPosition = cardFinalPositionView.getLocationOnScreen().x.toFloat()-
                    sampleCard.getLocationOnScreen().x.toFloat()

            val translationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f, finalXPosition)


            val animator =
                ObjectAnimator.ofPropertyValuesHolder(sampleCard, scaleX, scaleY, translationY,translationX)
                    .apply {
                        duration = animationDuration
                        interpolator = AccelerateInterpolator()
                    }
            animator.start()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    sampleCard.visibility = View.INVISIBLE
                    sampleCard.translationX=0f
                    sampleCard.translationY=0f
                    cardFinalPositionView.visibility = View.VISIBLE
                    onAnimationEnd()
                }
            })

        }

        fun arrangeCards(isOtherPlayer: Boolean,firstCard: View, secondCard: View) {


            //Means user has only one card
            if (firstCard===secondCard) return

            val marginBetweenCards=if (isOtherPlayer) 0 else 10

            val translationXValue = (firstCard.width / 2).toFloat()
            val translationX =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f, -translationXValue-marginBetweenCards)

            val rotationValue = 10f
            val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, -rotationValue)

            val animator =
                ObjectAnimator.ofPropertyValuesHolder(firstCard, translationX, rotation)
                    .apply {
                        duration = DURATION_ARRANGE_CARDS_ANIMATION
                        interpolator = AccelerateInterpolator()
                    }

            val translationX2 =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f, translationXValue+marginBetweenCards)
            val rotation2 = PropertyValuesHolder.ofFloat(View.ROTATION, rotationValue)
            val animator2 = ObjectAnimator.ofPropertyValuesHolder(secondCard, translationX2, rotation2)
                .apply {
                    duration = DURATION_ARRANGE_CARDS_ANIMATION
                    interpolator = AccelerateInterpolator()
                }

            animator.start()
            animator2.start()

            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
//                    firstCard.translationX = 0f
//                    secondCard.translationX=0f
                }
            })


        }


    }
}