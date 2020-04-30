package com.mmk.lovelettercardgame.ui.fragments.game


import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.dialogs.cardinfo.CardDetailInfoDialog
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.dpToPx
import com.mmk.lovelettercardgame.utils.inflate
import kotlinx.android.synthetic.main.fragment_game.*


/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    private var roomItem: RoomPOJO? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = container?.inflate(R.layout.fragment_game)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomItem = arguments?.getSerializable(RoomsFragment.ARGUMEN_ROOM_ITEM) as RoomPOJO?
        setClicks()
        giveCardToPlayer()

    }

    private fun setClicks() {
        image_view_game_player_card_1.setOnClickListener { cardItemClicked(image_view_game_player_card_1) }
        image_view_game_player_card_2.setOnClickListener { cardItemClicked(image_view_game_player_card_2) }

    }


    private fun cardItemClicked(cardImageView: ImageView) {
        CardDetailInfoDialog(
            activity,
            cardImageView
        ).show()
    }

    fun giveCardToPlayer(){

        val translationX:Float=context?.resources
            ?.getDimensionPixelSize(R.dimen.player_card_width)?.toFloat()?:0f

       val translationAnimator=ObjectAnimator.ofFloat(image_view_game_player_card_1,
           View.TRANSLATION_X,-translationX/2).apply {
           duration=1000

       }
        val rotationAnimator=ObjectAnimator.ofFloat(image_view_game_player_card_1,
        View.ROTATION,-10f).apply { duration=1000 }

        val set=AnimatorSet()
        set.playTogether(translationAnimator,rotationAnimator)
        set.start()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                image_view_game_player_card_1.translationX=0f
                image_view_game_player_card_2.visibility=View.VISIBLE
            }
        })
    }


}
