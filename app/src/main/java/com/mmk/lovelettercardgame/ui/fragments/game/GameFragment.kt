package com.mmk.lovelettercardgame.ui.fragments.game


import android.animation.*
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.dialogs.cardinfo.CardDetailInfoDialog
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import com.mmk.lovelettercardgame.utils.dpToPx
import com.mmk.lovelettercardgame.utils.getLocationOnScreen
import com.mmk.lovelettercardgame.utils.inflate
import com.mmk.lovelettercardgame.utils.toast
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.user_box_view.view.*


/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    private var roomItem: RoomPOJO? = null
    private var userBoxList= mutableListOf<View>()
    private val playersList= mutableListOf<PlayerPOJO>(PlayerPOJO("1","Salam")
        ,PlayerPOJO("2","Salam"),PlayerPOJO("3","Salam"),PlayerPOJO("4","Salam"))


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

        initView()
        setClicks()
        initFakePlayers()
        //giveCardToPlayer()

    }

    private fun initView() {
        userBoxList.add(layout_game_player_1)
        userBoxList.add(layout_game_player_2)
        userBoxList.add(layout_game_player_3)
        userBoxList.add(layout_game_player_4)

    }

    private fun initFakePlayers() {
        playersList.forEachIndexed { index, playerPOJO ->
            userBoxList[index].tag=playerPOJO.id
        }
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

        image_view_game_options.setOnClickListener {


            playersList.forEachIndexed { index, playerPOJO ->
                val duration=CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION+
                        CardAnimations.DURATION_DEAL_CARD_ANIMATION

                Handler().postDelayed({
                    giveCardToPlayer(playerPOJO)
                },duration*index)

            }




        }

    }


    private fun cardItemClicked(cardImageView: ImageView) {
        CardDetailInfoDialog(
            activity,
            cardImageView
        ).show()
    }


    fun giveCardToPlayer(playerPOJO: PlayerPOJO) {

        val rootUserBoxView = layout_game_fragment_container.findViewWithTag<View>(playerPOJO.id)

        //Means it is you
        var isOtherPlayer: Boolean
        var firstCard = image_view_game_player_card_1
        var secondCard = image_view_game_player_card_2
        if (rootUserBoxView === layout_game_player_1) {
            isOtherPlayer = false
            firstCard = image_view_game_player_card_1
            secondCard = image_view_game_player_card_2
        } else {
            isOtherPlayer = true
            firstCard = rootUserBoxView.image_view_userBox_card_1
            secondCard = rootUserBoxView.image_view_userBox_card_2
        }

        val sampleCard = image_view_game_card_sample_card
        sampleCard.visibility = View.VISIBLE

        var cardFinalPositionView = firstCard
        if (playerPOJO.cardsNumber == 1)
            cardFinalPositionView = secondCard

        CardAnimations.dealCard(isOtherPlayer, sampleCard, cardFinalPositionView) {
            playerPOJO.cardsNumber++
            CardAnimations.arrangeCards(isOtherPlayer, firstCard, cardFinalPositionView)
        }




    }


}
