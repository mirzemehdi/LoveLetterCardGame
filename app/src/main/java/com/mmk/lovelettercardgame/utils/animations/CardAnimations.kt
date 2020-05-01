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

        fun dealCard(sampleCard: View, cardFinalPositionView: View, onAnimationEnd: () -> Unit) {
            sampleCard.visibility = View.VISIBLE
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 4f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 4f)
            val finalYPosition = cardFinalPositionView.getLocationOnScreen().y.toFloat()
            val translationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, finalYPosition)

            val animator =
                ObjectAnimator.ofPropertyValuesHolder(sampleCard, scaleX, scaleY, translationY)
                    .apply {
                        duration = 1000
                        interpolator = AccelerateInterpolator()
                    }
            animator.start()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    sampleCard.visibility = View.INVISIBLE
                    cardFinalPositionView.visibility = View.VISIBLE
                    onAnimationEnd()
                }
            })

        }

        fun arrangeCards(firstCard: View, secondCard: View?) {

            if (secondCard == null) return

            val translationXValue = (firstCard.width / 2).toFloat()
            val translationX =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f, -translationXValue-10)

            val rotationValue = 10f
            val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, -rotationValue)

            val animator =
                ObjectAnimator.ofPropertyValuesHolder(firstCard, translationX, rotation)
                    .apply {
                        duration = 400
                        interpolator = AccelerateInterpolator()
                    }

            val translationX2 =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f, translationXValue+10)
            val rotation2 = PropertyValuesHolder.ofFloat(View.ROTATION, rotationValue)
            val animator2 = ObjectAnimator.ofPropertyValuesHolder(secondCard, translationX2, rotation2)
                .apply {
                    duration = 400
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