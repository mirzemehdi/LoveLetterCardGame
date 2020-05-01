package com.mmk.lovelettercardgame.ui.fragments.game


import android.animation.*
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.dialogs.cardinfo.CardDetailInfoDialog
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import com.mmk.lovelettercardgame.utils.dpToPx
import com.mmk.lovelettercardgame.utils.getLocationOnScreen
import com.mmk.lovelettercardgame.utils.inflate
import com.mmk.lovelettercardgame.utils.toast
import kotlinx.android.synthetic.main.fragment_game.*


/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    private var roomItem: RoomPOJO? = null
    var count=0


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
        //giveCardToPlayer()

    }

    private fun setClicks() {
        image_view_game_player_card_1.setOnClickListener {
            cardItemClicked(
                image_view_game_player_card_1
            )
        }
        image_view_game_player_card_2.setOnClickListener {
            cardItemClicked(
                image_view_game_player_card_2
            )
        }

        image_view_game_options.setOnClickListener { giveCardToPlayer() }

    }


    private fun cardItemClicked(cardImageView: ImageView) {
        CardDetailInfoDialog(
            activity,
            cardImageView
        ).show()
    }



    fun giveCardToPlayer() {
        val sampleCard=image_view_game_card_sample_card
        sampleCard.visibility=View.VISIBLE

        var cardFinalPositionView=image_view_game_player_card_1
        if (count==1)
            cardFinalPositionView=image_view_game_player_card_2

        CardAnimations.dealCard(sampleCard,cardFinalPositionView){
            //TODO do whatever you like when animation ends
            count++
            arrange()

        }







    }

    fun arrange() {

        val firstCard:View=image_view_game_player_card_1
        var secondCard:View?=image_view_game_player_card_2
        if (count<2)
            secondCard=null
        CardAnimations.arrangeCards(firstCard,secondCard)



    }


}
