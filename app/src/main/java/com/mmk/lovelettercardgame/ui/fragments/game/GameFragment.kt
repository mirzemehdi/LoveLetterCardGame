package com.mmk.lovelettercardgame.ui.fragments.game


import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.CardPojo
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.activities.main.MainActivity
import com.mmk.lovelettercardgame.ui.dialogs.allcards.AllCardsDialog
import com.mmk.lovelettercardgame.ui.dialogs.cardinfo.CardDetailInfoDialog
import com.mmk.lovelettercardgame.ui.dialogs.joinroom.JoinRoomDialog
import com.mmk.lovelettercardgame.ui.dialogs.otherplayercard.OtherPlayerCardDialog
import com.mmk.lovelettercardgame.ui.dialogs.playedcard.PlayedCardDialog
import com.mmk.lovelettercardgame.ui.dialogs.swapcards.SwapCardsDialog
import com.mmk.lovelettercardgame.ui.fragments.menu.MenuFragment
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.*
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import com.mmk.lovelettercardgame.utils.helpers.CardsHolder
import com.mmk.lovelettercardgame.utils.helpers.Helper
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.user_box_view.view.*
import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock


/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment(R.layout.fragment_game), GameContractor.View {

    private var backgroundMusicPlayer: MediaPlayer? = null

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
        roomItem = arguments?.getSerializable(RoomsFragment.ARGUMEN_ROOM_ITEM) as RoomPOJO?



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("RoomItem $roomItem")

        initView()
        backgroundMusicPlayer = MediaPlayer.create(context, R.raw.bg_game_music)
        backgroundMusicPlayer?.isLooping = true
        fragmentManager?.addOnBackStackChangedListener {
            println("backstack called")
            val volume=Constants.getVolume(Constants.CURRENT_VOLUME_MUSIC)
            backgroundMusicPlayer?.setVolume(volume,volume)
        }

        setClicks()

        mPresenter.getPlayers(roomItem)
        //TODO REMOVE BelowLine
        //Handler().postDelayed({ mPresenter.getPlayers(roomItem) }, 500)


//
        if (roomItem != null && myPlayer == null) {
            joinRoomDialog =
                JoinRoomDialog(getActivityOfActivity(), roomItem?.id!!) { joinedPlayer ->
                    //Player is Joined
                    mPresenter.joinGame(joinedPlayer)
                }
            joinRoomDialog?.show()
        }


    }

    override fun onPause() {
        super.onPause()
        backgroundMusicPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        backgroundMusicPlayer?.start()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        isViewStopped = true
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.closeServer()
        backgroundMusicPlayer?.stop()

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

        image_view_game_options.setOnClickListener {
            val hostActivity = getActivityOfActivity() as MainActivity
            hostActivity.changeFragment(MenuFragment(true), false)
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
                userBoxView.image_view_userBox_card_1.visibility = View.INVISIBLE
            }

        }
    }

    private fun initUserBoxView(userBoxView: View, playerPOJO: PlayerPOJO) {
        val tokensNumber = when (roomItem?.maxNbPlayers) {
            2 -> 7
            3 -> 5
            4 -> 4
            else -> 4
        }
        userBoxView.apply {
            visibility = View.VISIBLE
            tag = playerPOJO.id
            val playerNameTextView = userBoxView.textView_userBox_playerName
            playerNameTextView.text = playerPOJO.name
            if (playerPOJO.id == myPlayer?.id) playerNameTextView.text =
                getString(R.string.text_you)
            image_view_userBox_card_1.apply {
                rotation = 0f
                translationX = 0f
            }

            if (playerPOJO.cardsCount != 2)
                image_view_userBox_card_2.visibility = View.GONE



            userBoxView.alpha = 1f
            for (i in 0 until tokensNumber) {
                val imageView: ImageView = linearLayout_userBox_tokensView[i] as ImageView
                imageView.visibility = View.VISIBLE
                if (i < playerPOJO.points.toInt()) imageView.setImageResource(R.drawable.ic_heart_fill)
                else imageView.setImageResource(R.drawable.ic_heart)
            }
        }
        setDiscardedCards(playerPOJO)

    }

    override fun setDiscardedCards(playerPOJO: PlayerPOJO) {

        val discardedCardTypes = playerPOJO.discardedCards.map { card ->
            card.power
        }
        println("Discarded Card Types: $discardedCardTypes")
        val userBoxView = layout_game_fragment_container.findViewWithTag<View>(playerPOJO.id)


        CardsHolder.setCardList(
            userBoxView.frame_layout_user_box_player_cards,
            discardedCardTypes,
            getContextOfActivity()
        )
    }

    override fun saveMyOwnPlayer(playerPOJO: PlayerPOJO) {
        myPlayer = playerPOJO



    }

    override fun giveCardToAllPlayers(players: List<PlayerPOJO>?) {

        players?.forEachIndexed { index, playerPOJO ->
            val duration = CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION +
                    CardAnimations.DURATION_DEAL_CARD_ANIMATION

            //  cardWaitingPlayers.add(playerPOJO.id)
            giveCardToPlayer(playerPOJO.id)

        }


    }


    override fun giveCardToPlayer(givenPlayerId: String?): Unit {


        if (givenPlayerId != null) {
            cardWaitingPlayers.add(givenPlayerId)
            if (cardWaitingPlayers.size > 1) {
                println("Give Card size>1")
                return@giveCardToPlayer//Because animation end will call
            }
        }
        println("Give Card started")
        val playerId = cardWaitingPlayers.peek() ?: return
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
                cardWaitingPlayers.poll()
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
        if (cards.isEmpty()) {
            image_view_game_player_card_1.visibility = View.INVISIBLE
            image_view_game_player_card_2.visibility = View.GONE
        }
        if (cards.size == 1) {

            image_view_game_player_card_2.visibility = View.GONE
            image_view_game_player_card_1.rotation = 0f
            image_view_game_player_card_1.translationX = 0f

            val cardResourceId = getActivityOfActivity()
                ?.getGameCardResourceId(cards[0].power) ?: R.drawable.card_back
            image_view_game_player_card_1
                .setImageResource(cardResourceId)
            image_view_game_player_card_1.tag = cards[0]

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

    override fun newCardPlayed(playerId: String, cardType: Int, targetPlayerId: String?) {
        val playerName = mPlayers?.find { it.id == playerId }?.name ?: ""
        val targetPlayerName = mPlayers?.find { it.id == targetPlayerId }?.name ?: ""

        PlayedCardDialog(getActivityOfActivity(), cardType, playerName, targetPlayerName)
            .show()

    }

    override fun roundFinished(playerPOJO: PlayerPOJO) {

        hideAllCards()
        Helper.showDialog(
            getContextOfActivity(),
            getContextOfActivity()?.getString(R.string.round_finished_dialog_title),
            getContextOfActivity()?.getString(
                R.string.round_finished_dialog_message,
                playerPOJO.name
            )
        )

    }

    private fun hideAllCards() {
        userBoxList.forEach {
            it.image_view_userBox_card_1.visibility = View.INVISIBLE
            it.image_view_userBox_card_2.visibility = View.GONE
            it.image_view_userBox_card_1.translationX = 0f
            it.image_view_userBox_card_2.translationX = 0f
            it.image_view_userBox_card_1.rotation = 0f
            it.image_view_userBox_card_2.rotation = 0f
        }
        image_view_game_player_card_1.visibility = View.INVISIBLE
        image_view_game_player_card_2.visibility = View.GONE
        image_view_game_player_card_1.translationX = 0f
        image_view_game_player_card_1.rotation = 0f
        image_view_game_player_card_2.translationX = 0f
        image_view_game_player_card_2.rotation = 0f
    }

    override fun gameFinished(playerPOJO: PlayerPOJO) {
        hideAllCards()
        Helper.showDialog(
            getContextOfActivity(),
            getContextOfActivity()?.getString(R.string.game_finished_dialog_title),
            getContextOfActivity()?.getString(
                R.string.game_finished_dialog_message,
                playerPOJO.name
            )
        )

    }

    override fun swapCards(firstPlayerId: String, secondPlayerId: String?) {
        if (isViewStopped) return
        if (secondPlayerId == null) return
        val firstPlayerUserBoxView =
            layout_game_fragment_container.findViewWithTag<View>(firstPlayerId)
        val secondPlayerUserBoxView =
            layout_game_fragment_container.findViewWithTag<View>(secondPlayerId)

        if (firstPlayerUserBoxView == null || secondPlayerUserBoxView == null) return

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
