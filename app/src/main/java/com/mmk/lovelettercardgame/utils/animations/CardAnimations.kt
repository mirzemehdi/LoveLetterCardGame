package com.mmk.lovelettercardgame.utils.animations

import android.animation.*
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.utils.getLocationOnScreen
import com.mmk.lovelettercardgame.utils.helpers.CurveExchangeTransformator
import com.mmk.lovelettercardgame.utils.helpers.ExchangeCoords
import com.mmk.lovelettercardgame.utils.helpers.ExchangeTransformator
import kotlin.math.max
import kotlin.math.sqrt


class CardAnimations {
    companion object {
        const val DURATION_DEAL_CARD_ANIMATION=500L
        const val DURATION_ARRANGE_CARDS_ANIMATION=200L
        const val DURATION_SWAP_CARDS_ANIMATION=800L


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

        fun swapCards(firstCard: View,secondCard: View,onAnimationEnd: () -> Unit={}){

            val transformator: ExchangeTransformator = CurveExchangeTransformator(
                ExchangeCoords(firstCard.x, firstCard.y, secondCard.x, secondCard.y),
                max(firstCard.measuredWidth, secondCard.measuredHeight).toFloat()
            )
            val animator = ValueAnimator.ofFloat(0f, 1f)
            animator.duration = DURATION_SWAP_CARDS_ANIMATION
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.addUpdateListener { valueAnimator ->
                val (sourceX, sourceY, targetX, targetY) = transformator.transform(valueAnimator.animatedFraction)


                firstCard.x = targetX
                firstCard.y = targetY

                secondCard.x = sourceX
                secondCard.y = sourceY
            }
            animator.start()
            animator.addListener(object :AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    Handler().postDelayed({onAnimationEnd()},200)

                }
            })

        }


    }
}