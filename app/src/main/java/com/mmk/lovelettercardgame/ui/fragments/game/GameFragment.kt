package com.mmk.lovelettercardgame.ui.fragments.game


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.dialogs.cardinfo.CardDetailInfoDialog
import com.mmk.lovelettercardgame.ui.dialogs.joinroom.JoinRoomDialog
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.CardMoveListener
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import com.mmk.lovelettercardgame.utils.inflate
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.user_box_view.view.*


/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment(), GameContractor.View {

    private var isViewStopped=false
    private lateinit var mPresenter: GameContractor.Presenter
    private var myPlayer: PlayerPOJO? = null
    private var roomItem: RoomPOJO? = null
    private val progressBar by lazy { progress_game_fragment }
    private var userBoxList = mutableListOf<View>()
    private lateinit var clickableAnimation: Animation
    private var joinRoomDialog:JoinRoomDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GamePresenter(this)


    }


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
        println("RoomItem $roomItem")

        initView()
        setClicks()
        mPresenter.getPlayers(roomItem)

        joinRoomDialog=JoinRoomDialog(getActivityOfActivity(),roomItem?.id!!){joinedPlayer->
            //Player is Joined
            mPresenter.joinGame(joinedPlayer)
        }
        joinRoomDialog?.show()

//        Handler().postDelayed(Runnable {
//            mPresenter.joinGame("Ramin")
//        }, 3000)


        //giveCardToPlayer()

    }

    private fun initView() {
        isViewStopped=false
        userBoxList.add(layout_game_player_1)
        userBoxList.add(layout_game_player_2)
        userBoxList.add(layout_game_player_3)
        userBoxList.add(layout_game_player_4)
        userBoxList.forEach { view -> view.visibility = View.GONE }
        progressBar.visibility = View.VISIBLE
        clickableAnimation = AnimationUtils.loadAnimation(context, R.anim.clickable)

        image_view_game_player_card_1.setOnTouchListener(CardMoveListener())
        image_view_game_player_card_2.setOnTouchListener(CardMoveListener())
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


    }


    private fun cardItemClicked(cardImageView: ImageView) {
        CardDetailInfoDialog(
            activity,
            cardImageView
        ).show()
    }


    override fun showPlayers(players: List<PlayerPOJO>) {
        progressBar.visibility = View.GONE
        if (myPlayer == null) {
            players.forEachIndexed { index, playerPOJO ->
                initUserBoxView(userBoxList[index], playerPOJO)
            }
        } else {
            var myPlayerIndex = 0
            players.forEachIndexed { index, playerPOJO ->
                if (myPlayer!!.id == playerPOJO.id) myPlayerIndex = index
            }

            players.forEachIndexed { index, playerPOJO ->
                val indexOfPlayerInBox = (players.size + index - myPlayerIndex) % players.size
                initUserBoxView(userBoxList[indexOfPlayerInBox], playerPOJO)

            }
        }
    }

    private fun initUserBoxView(userBoxView: View, playerPOJO: PlayerPOJO) {
        userBoxView.apply {
            visibility = View.VISIBLE
            tag = playerPOJO.id
            val playerNameTextView = userBoxView.textView_userBox_playerName
            playerNameTextView.text = playerPOJO.name
            if (playerPOJO==myPlayer) playerNameTextView.text=getString(R.string.text_you)

        }

    }

    override fun saveMyOwnPlayer(playerPOJO: PlayerPOJO) {
        myPlayer = playerPOJO
    }

    override fun giveCardToAllPlayers(players: List<PlayerPOJO>?) {
        players?.forEachIndexed { index, playerPOJO ->
            val duration = CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION +
                    CardAnimations.DURATION_DEAL_CARD_ANIMATION


            Handler().postDelayed({
                if (!isViewStopped)
                    giveCardToPlayer(playerPOJO)
            }, duration * index)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewStopped=true
    }


    override fun giveCardToPlayer(playerPOJO: PlayerPOJO?) {
        if (isViewStopped) return
        val rootUserBoxView = layout_game_fragment_container.findViewWithTag<View>(playerPOJO?.id)


        //Means it is you
        val isOtherPlayer: Boolean
        val firstCard: ImageView
        val secondCard: ImageView
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
        if (playerPOJO?.cardsCount == 1)
            cardFinalPositionView = secondCard

        CardAnimations.dealCard(isOtherPlayer, sampleCard, cardFinalPositionView) {
            if (playerPOJO!=null) {
                playerPOJO.cardsCount++
                CardAnimations.arrangeCards(isOtherPlayer, firstCard, cardFinalPositionView)
            }
        }



    }

    override fun hideShowWaitingText(isWaitingPlayers: Boolean) {
        text_view_game_waiting_players.visibility =
            if (isWaitingPlayers) View.VISIBLE else View.GONE
    }

    override fun makeTurnOfPlayer(playerPOJO: PlayerPOJO?) {
        if (isViewStopped) return
        val rootUserBoxView = layout_game_fragment_container.findViewWithTag<View>(playerPOJO?.id)
        userBoxList.forEach {
            if (it===rootUserBoxView) it.startAnimation(clickableAnimation)
            else{
                it.clearAnimation()
                clickableAnimation.cancel()
                clickableAnimation.reset()
            }
        }

    }

    override fun getActivityOfActivity(): Activity? = activity

    override fun getContextOfActivity(): Context? = context

    override fun setPresenter(presenter: GamePresenter) {
        mPresenter = presenter
    }
}
