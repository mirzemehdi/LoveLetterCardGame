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
import com.mmk.lovelettercardgame.ui.dialogs.otherplayercard.OtherPlayerCardDialog
import com.mmk.lovelettercardgame.ui.dialogs.swapcards.SwapCardsDialog
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.*
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import com.mmk.lovelettercardgame.utils.helpers.CardsHolder
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.user_box_view.view.*
import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock


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
    private val cardWaitingPlayers: Queue<String> = LinkedList()
    private var mPlayers: List<PlayerPOJO>? = null


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
        //TODO REMOVE BelowLine
        //Handler().postDelayed({ mPresenter.getPlayers(roomItem) }, 500)


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

        userBoxList.forEach {
            it.setOnDragListener(CardMoveListener() { cardPOJO, targetPlayerId ->
                onCardPlayed(
                    cardPOJO,
                    targetPlayerId
                )
            })
        }


        image_view_game_player_card_1.setOnLongClickListener(CardMoveListener { cardPOJO, targetPlayerId ->
            onCardPlayed(
                cardPOJO,
                targetPlayerId
            )
        })
        image_view_game_player_card_2.setOnLongClickListener(CardMoveListener { cardPOJO, targetPlayerId ->
            onCardPlayed(
                cardPOJO,
                targetPlayerId
            )
        })

        image_view_game_player_card_1.setOnDragListener(CardMoveListener() { cardPOJO, targetPlayerId ->
            onCardPlayed(
                cardPOJO,
                targetPlayerId
            )
        })
        image_view_game_player_card_2.setOnDragListener(CardMoveListener() { cardPOJO, targetPlayerId ->
            onCardPlayed(
                cardPOJO,
                targetPlayerId
            )
        })
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

    override fun onCardPlayed(cardPojo: CardPojo, targetPlayerId: String?) {
        println("onCardPlayed: $cardPojo and player: $targetPlayerId")
        mPresenter.playCard(cardPojo, targetPlayerId)

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
        mPlayers = players
    }

    override fun playersStateUpdated(players: List<PlayerPOJO>) {
        players.forEach { player ->
            val userBoxView = layout_game_fragment_container.findViewWithTag<View>(player.id)
            initUserBoxView(userBoxView, player)
        }

        mPlayers?.forEach { player ->
            val isInGame = players.any { updatePlayer -> updatePlayer.id == player.id }
            if (!isInGame) {
                val userBoxView = layout_game_fragment_container.findViewWithTag<View>(player.id)
                userBoxView.alpha = 0.5f
                userBoxView.image_view_userBox_card_1.visibility = View.GONE
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
            image_view_userBox_card_1.apply {
                rotation = 0f
                translationX = 0f
            }
            image_view_userBox_card_2.visibility = View.GONE

            val discardedCardTypes = playerPOJO.discardedCards.map { card ->
                card.power
            }
            println("Discarded Card Types: $discardedCardTypes")
            CardsHolder.setCardList(
                frame_layout_user_box_player_cards,
                discardedCardTypes,
                getContextOfActivity()
            )
            userBoxView.alpha = 1f
        }

    }

    override fun saveMyOwnPlayer(playerPOJO: PlayerPOJO) {
        myPlayer = playerPOJO
    }

    override fun giveCardToAllPlayers(players: List<PlayerPOJO>?) {

        players?.forEachIndexed { index, playerPOJO ->
            val duration = CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION +
                    CardAnimations.DURATION_DEAL_CARD_ANIMATION

            cardWaitingPlayers.add(playerPOJO.id)


        }
        giveCardToPlayer(null)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewStopped = true
    }


    override fun giveCardToPlayer(givenPlayerId: String?) {

        println("Give card started")
        if (givenPlayerId != null) {
            cardWaitingPlayers.add(givenPlayerId)
            if (cardWaitingPlayers.size > 1) return //Because animation end will call
        }
        val playerId = cardWaitingPlayers.poll() ?: return
        val rootUserBoxView = layout_game_fragment_container.findViewWithTag<View>(playerId)
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
        val cardsCount = getPlayersCardCount(playerId, isOtherPlayer)
        println("Cards count $cardsCount")
        if (cardsCount == 1)
            cardFinalPositionView = secondCard

        CardAnimations.dealCard(isOtherPlayer, sampleCard, cardFinalPositionView) {
            CardAnimations.arrangeCards(isOtherPlayer, firstCard, cardFinalPositionView) {
                println("Give Card Arrange called")
                giveCardToPlayer(null)

            }

        }


        println("Give card finished")

    }

    private fun getPlayersCardCount(playerId: String, isOtherPlayer: Boolean): Int {
        val rootUserBoxView = layout_game_fragment_container.findViewWithTag<View>(playerId)
        var cardsCount = 0
        if (isOtherPlayer) {
            if (rootUserBoxView.image_view_userBox_card_1.visibility == View.VISIBLE)
                cardsCount++
        } else {
            if (image_view_game_player_card_1.visibility == View.VISIBLE)
                cardsCount++

        }

        return cardsCount
    }

    override fun hideShowWaitingText(isWaitingPlayers: Boolean) {
        text_view_game_waiting_players.visibility =
            if (isWaitingPlayers) View.VISIBLE else View.GONE
    }

    override fun makeTurnOfPlayer(playerId: String) {
        if (isViewStopped) return
        val rootUserBoxView = layout_game_fragment_container.findViewWithTag<View>(playerId)
        onMyTurn(rootUserBoxView === layout_game_player_1)
        userBoxList.forEach {
            if (it === rootUserBoxView) it.startAnimation(clickableAnimation)
            else {
                it.clearAnimation()
                clickableAnimation.cancel()
                clickableAnimation.reset()
            }
        }

    }

    private fun onMyTurn(isMyTurn: Boolean) {
        image_view_game_player_card_1.isLongClickable = isMyTurn
        image_view_game_player_card_2.isLongClickable = isMyTurn
//        if (isMyTurn){
//
//        }
//        else{
//
//        }
    }

    override fun showMessage(message: String?, messageType: Constants.MessageType) {
        if (message != null)
            getContextOfActivity()?.toasty(message, messageType)
    }

    override fun myCardsUpdated(cards: List<CardPojo>) {
        if (cards.size == 1) {

            image_view_game_player_card_2.visibility = View.GONE
            image_view_game_player_card_1.rotation = 0f
            image_view_game_player_card_1.translationX = 0f

            val cardResourceId = getActivityOfActivity()
                ?.getGameCardResourceId(cards[0].power) ?: R.drawable.card_back
            image_view_game_player_card_1
                .setImageResource(cardResourceId)
            image_view_game_player_card_1.tag = cards[0]
            image_view_game_player_card_2.visibility = View.GONE
        } else if (cards.size == 2) {

            val cardFirstResourceId = getActivityOfActivity()
                ?.getGameCardResourceId(cards[0].power) ?: R.drawable.card_back
            val cardSecondResourceId = getActivityOfActivity()
                ?.getGameCardResourceId(cards[1].power) ?: R.drawable.card_back

            image_view_game_player_card_1
                .setImageResource(cardFirstResourceId)
            image_view_game_player_card_1.tag = cards[0]
            image_view_game_player_card_2
                .setImageResource(cardSecondResourceId)
            image_view_game_player_card_2.tag = cards[1]
        }
    }

    override fun lookOtherPlayerCard(playerId: String, cardType: Int) {
        val playerName = mPlayers?.find { it.id == playerId }?.name ?: ""
        OtherPlayerCardDialog(getActivityOfActivity(), cardType, playerName).show()
    }

    override fun swapCards(firstPlayerId: String, secondPlayerId: String) {
        if (isViewStopped) return

        val firstPlayerUserBoxView =
            layout_game_fragment_container.findViewWithTag<View>(firstPlayerId)
        val secondPlayerUserBoxView =
            layout_game_fragment_container.findViewWithTag<View>(secondPlayerId)

        if (firstPlayerUserBoxView==null || secondPlayerUserBoxView==null) return

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
            if (firstPlayerId == myPlayer?.id) getString(R.string.text_you) else mPlayers?.find { it.id == firstPlayerId }?.name
        val secondPlayerName =
            if (secondPlayerId == myPlayer?.id) getString(R.string.text_you) else mPlayers?.find { it.id == firstPlayerId }?.name
        SwapCardsDialog(
            getActivityOfActivity(),
            firstCard, secondCard,
            firstPlayerName ?: "", secondPlayerName ?: ""

        )
            .show()


    }

    override fun addToDiscardedCard(playerId: String, cardType: Int) {

        val userBoxView = layout_game_fragment_container.findViewWithTag<View>(playerId)
        val discardedCardsLayout = userBoxView.frame_layout_user_box_player_cards
        CardsHolder.addCard(discardedCardsLayout, cardType, getContextOfActivity())
        if (playerId != myPlayer?.id) {

            if (userBoxView.image_view_userBox_card_2.visibility != View.VISIBLE)
                userBoxView.image_view_userBox_card_1.visibility = View.GONE
            else
                userBoxView.image_view_userBox_card_2.visibility = View.GONE
        }
    }

    override fun getActivityOfActivity(): Activity? = activity

    override fun getContextOfActivity(): Context? = context

    override fun setPresenter(presenter: GamePresenter) {
        mPresenter = presenter
    }
}
