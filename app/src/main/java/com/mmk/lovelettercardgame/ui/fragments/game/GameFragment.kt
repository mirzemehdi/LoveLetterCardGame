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
import com.mmk.lovelettercardgame.pojo.CardPojo
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.dialogs.allcards.AllCardsDialog
import com.mmk.lovelettercardgame.ui.dialogs.cardinfo.CardDetailInfoDialog
import com.mmk.lovelettercardgame.ui.dialogs.joinroom.JoinRoomDialog
import com.mmk.lovelettercardgame.ui.dialogs.swapcards.SwapCardsDialog
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.*
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import com.mmk.lovelettercardgame.utils.helpers.CardsHolder
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.user_box_view.*
import kotlinx.android.synthetic.main.user_box_view.view.*
import kotlinx.android.synthetic.main.user_box_view.view.frame_layout_user_box_player_cards


/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment(), GameContractor.View {

    private var isViewStopped = false
    private lateinit var mPresenter: GameContractor.Presenter
    private var myPlayer: PlayerPOJO? = null
    private var roomItem: RoomPOJO? = null
    private val progressBar by lazy { progress_game_fragment }
    private var userBoxList = mutableListOf<View>()
    private lateinit var clickableAnimation: Animation
    private var joinRoomDialog: JoinRoomDialog? = null

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

        //TODO REMOVE HANDLER
        Handler().postDelayed({ mPresenter.getPlayers(roomItem) }, 500)


//
        if (roomItem != null) {
            joinRoomDialog =
                JoinRoomDialog(getActivityOfActivity(), roomItem?.id!!) { joinedPlayer ->
                    //Player is Joined
                    mPresenter.joinGame(joinedPlayer)
                }
            joinRoomDialog?.show()
        }


    }

    private fun initView() {
        isViewStopped = false
        userBoxList.add(layout_game_player_1)
        userBoxList.add(layout_game_player_2)
        userBoxList.add(layout_game_player_3)
        userBoxList.add(layout_game_player_4)
        userBoxList.forEach { view -> view.visibility = View.GONE }
        progressBar.visibility = View.VISIBLE
        clickableAnimation = AnimationUtils.loadAnimation(context, R.anim.clickable)

        userBoxList.forEach { it.setOnDragListener(CardMoveListener()) }


        image_view_game_player_card_1.setOnLongClickListener(CardMoveListener())
        image_view_game_player_card_1.setOnDragListener(CardMoveListener())
        image_view_game_player_card_2.setOnDragListener(CardMoveListener())
        image_view_game_player_card_2.setOnLongClickListener(CardMoveListener())
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
            if (playerPOJO == myPlayer) playerNameTextView.text = getString(R.string.text_you)

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
        isViewStopped = true
    }


    override fun giveCardToPlayer(playerPOJO: PlayerPOJO?) {
        if (isViewStopped) return
        val rootUserBoxView = layout_game_fragment_container.findViewWithTag<View>(playerPOJO?.id)
        val sampleCard = image_view_game_card_sample_card
        sampleCard.visibility = View.VISIBLE
        sampleCard.setImageResource(R.drawable.card_back)

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


        var cardFinalPositionView = firstCard
        if (playerPOJO?.cardsCount == 1)
            cardFinalPositionView = secondCard

        CardAnimations.dealCard(isOtherPlayer, sampleCard, cardFinalPositionView) {
            if (playerPOJO != null) {
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
            if (it === rootUserBoxView) it.startAnimation(clickableAnimation)
            else {
                it.clearAnimation()
                clickableAnimation.cancel()
                clickableAnimation.reset()
            }
        }

    }

    override fun showMessage(message: String?, messageType: Constants.MessageType) {
        if (message != null)
            getContextOfActivity()?.toasty(message, messageType)
    }

    override fun myCardsUpdated(cards: List<CardPojo>) {
        if (cards.size == 1) {
            val cardResourceId = getActivityOfActivity()
                ?.getGameCardResourceId(cards[0].power) ?: R.drawable.card_back
            image_view_game_player_card_1
                .setImageResource(cardResourceId)
            image_view_game_player_card_2.setImageResource(R.drawable.card_back)
        } else if (cards.size == 2) {
            val cardFirstResourceId = getActivityOfActivity()
                ?.getGameCardResourceId(cards[0].power) ?: R.drawable.card_back
            val cardSecondResourceId = getActivityOfActivity()
                ?.getGameCardResourceId(cards[1].power) ?: R.drawable.card_back

            image_view_game_player_card_1
                .setImageResource(cardFirstResourceId)
            image_view_game_player_card_2
                .setImageResource(cardSecondResourceId)
        }
    }

    override fun swapCards(firstPlayer: PlayerPOJO, secondPlayer: PlayerPOJO) {
        if (isViewStopped) return

        val firstPlayerUserBoxView =
            layout_game_fragment_container.findViewWithTag<View>(firstPlayer.id)
        val secondPlayerUserBoxView =
            layout_game_fragment_container.findViewWithTag<View>(secondPlayer.id)
        val firstCard: ImageView
        val secondCard: ImageView
        firstCard = if (firstPlayerUserBoxView === layout_game_player_1) {
            image_view_game_player_card_1
        } else {
            firstPlayerUserBoxView.image_view_userBox_card_1
        }
        secondCard = if (secondPlayerUserBoxView === layout_game_player_1) {
            image_view_game_player_card_1
        } else {
            secondPlayerUserBoxView.image_view_userBox_card_1
        }

        val firstPlayerName =
            if (firstPlayer.id == myPlayer?.id) getString(R.string.text_you) else firstPlayer.name
        val secondPlayerName =
            if (secondPlayer.id == myPlayer?.id) getString(R.string.text_you) else secondPlayer.name
        SwapCardsDialog(
            getActivityOfActivity(),
            firstCard, secondCard,
            firstPlayerName, secondPlayerName
        )
            .show()


    }

    override fun addToDiscardedCard(playerId:String,cardType: Int) {

        val userBoxView= layout_game_fragment_container.findViewWithTag<View>(playerId)
        val discardedCardsLayout=userBoxView.frame_layout_user_box_player_cards
        CardsHolder.addCard(discardedCardsLayout,cardType,getContextOfActivity())
    }

    override fun getActivityOfActivity(): Activity? = activity

    override fun getContextOfActivity(): Context? = context

    override fun setPresenter(presenter: GamePresenter) {
        mPresenter = presenter
    }
}
