package com.mmk.lovelettercardgame.ui.fragments.game

import android.os.Handler
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.*
import com.mmk.lovelettercardgame.ui.dialogs.allcards.AllCardsDialog
import com.mmk.lovelettercardgame.utils.Constants
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import org.json.JSONObject

class GamePresenter(private val mView: GameContractor.View) : GameContractor.Presenter {
    private val roomsIntractor = RoomsIntractor()
    private var myPlayer: PlayerPOJO? = null

    init {
        mView.setPresenter(this)
    }

    val playersList = mutableListOf(
        PlayerPOJO("1", "Anday")
        , PlayerPOJO("2", "Sirin")
        , PlayerPOJO("3", "Amine")
        , PlayerPOJO("4", "Ramin")
    )

    override fun closeServer() {
        roomsIntractor.closeServer()
    }

    override fun getPlayers(roomItem: RoomPOJO?) {
        listenForPlayersUpdate(roomItem?.maxNbPlayers)
        getMyCards()
        listenForPlayedCard()
        listenForNewPlayedCard()
        listerForPlayerUpdates()
        listenForTurn()
        listenForRoundFinished()
        listenForGameFinished()
        listerForLostPlayer()
        listenCardKingPlayed()
        listenCardPriestPlayed()
        listenCardPrincePlayed()
        mView.showPlayers(roomItem?.players ?: mutableListOf())

        //TODO REMOVE THESE 2 LINES
//        mView.showPlayers(playersList)
//        for (i in 1..2) {
//            mView.addToDiscardedCard("1", i)
//        }
//EVENT_PLAYER_LEVENT_PLAYER_L
//        startGame(playersList)


    }

    private fun listenForTurn() {
        roomsIntractor.getPlayerTurn(PlayerTurnListener())
    }

    private fun listenForPlayedCard() {
        roomsIntractor.listenPlayedCard(CardPlayedListener())
    }
    private fun listenForNewPlayedCard(){
        roomsIntractor.listernForNewCardPlayed(NewCardPlayedListener())
    }

    private fun listerForLostPlayer() {
        roomsIntractor.listenPlayerLost(PlayerLostListener())
    }

    private fun listerForPlayerUpdates() {
        roomsIntractor.listenActivePlayers(ActivePlayersListener())
    }

    override fun joinGame(playerPOJO: PlayerPOJO) {
        myPlayer = playerPOJO
        mView.saveMyOwnPlayer(playerPOJO)

    }

    override fun listenForPlayersUpdate(maxNbPlayers: Int?) {
        println("PlayersUpdateResponse listen")
        roomsIntractor.getPlayers(PlayersUpdateListener(maxNbPlayers))
    }

    override fun getMyCards() {
        roomsIntractor.getMyCards(MyCardsUpdateListener())
    }

    private fun startGame(players: List<PlayerPOJO>) {
        mView.giveCardToAllPlayers(players)
        mView.hideShowWaitingText(false)
//        Handler().postDelayed(Runnable {
//            mView.giveCardToPlayer(players[0])
//            mView.makeTurnOfPlayer(players[0])
//
//
//
//
//        }
//            ,
//            players.size * (CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION + CardAnimations.DURATION_DEAL_CARD_ANIMATION))


    }

    override fun playCard(cardPojo: CardPojo, targetPlayerId: String?) {

        if (cardPojo.power == CardPojo.TYPE_HANDMAID && targetPlayerId != myPlayer?.id) {
            mView.showMessage(
                mView.getActivityOfActivity()
                    ?.getString(R.string.toast_warning_handmaid_protect_other),
                Constants.MessageType.TYPE_WARNING
            )
            return
        }

        if (cardPojo.power == CardPojo.TYPE_GUARD) {
            AllCardsDialog(mView.getActivityOfActivity()) { guessedCardType ->
                roomsIntractor.playCard(cardPojo, targetPlayerId, guessedCardType)
            }.show()
        } else {
            roomsIntractor.playCard(cardPojo, targetPlayerId, null)
        }
    }

    override fun listenCardKingPlayed() {
        roomsIntractor.listenCardKing(KingPlayedListener())
    }

    override fun listenCardPrincePlayed() {
        roomsIntractor.listenCardPrince(PrincePlayedListener())
    }

    override fun listenCardPriestPlayed() {
        roomsIntractor.listenCardPriest(PriestPlayedListener())
    }
    private fun listenForGameFinished(){
        roomsIntractor.listenForGameFinished(GameFinishedListener())
    }
    private fun listenForRoundFinished(){
        roomsIntractor.listenForRoundFinished(RoundFinishedListener())
    }


    inner class RoundFinishedListener() : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response RoundFinished $data")
                val responseRoundFinishedPOJO =
                    Gson().fromJson(data.toString(), RoundFinishedPOJO::class.java)
                if (responseRoundFinishedPOJO.status == 200) {
                    mView.roundFinished(responseRoundFinishedPOJO.data)

                }

            }
        }
    }


    inner class GameFinishedListener() : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response GameFinished $data")
                val responseGameFinishedPOJO =
                    Gson().fromJson(data.toString(), RoundFinishedPOJO::class.java)
                if (responseGameFinishedPOJO.status == 200) {
                    mView.gameFinished(responseGameFinishedPOJO.data)
                }

            }
        }
    }


    inner class PlayersUpdateListener(val maxNbPlayers: Int?) : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response PlayersUpdate $data")
                val responsePlayersUpdate =
                    Gson().fromJson(data.toString(), ResponsePlayersUpdatePojo::class.java)
                if (responsePlayersUpdate.status == 200) {
                    mView.showPlayers(responsePlayersUpdate.data)

                    val players = responsePlayersUpdate.data
                    if (players.size == maxNbPlayers) {
                        startGame(players)
                    }

                }

            }
        }
    }

    inner class MyCardsUpdateListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response MyCards $data")
                val responseMyCards = Gson().fromJson(data.toString(), ResponseCardPOJO::class.java)
                if (responseMyCards.status == 200) {
                    mView.myCardsUpdated(responseMyCards.data)
                } else
                    mView.showMessage(
                        mView.getContextOfActivity()
                            ?.getString(R.string.toast_error_my_cards),
                        Constants.MessageType.TYPE_WARNING
                    )

            }
        }
    }

    inner class PlayerTurnListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response PlayerTurn $data")

                val responsePlayerTurn =
                    Gson().fromJson(data.toString(), ResponsePlayerTurn::class.java)

                if (responsePlayerTurn.status == 200) {
                    mView.makeTurnOfPlayer(responsePlayerTurn.playerId)
                    mView.giveCardToPlayer(responsePlayerTurn.playerId)

                } else
                    mView.showMessage(
                        mView.getContextOfActivity()
                            ?.getString(R.string.toast_error_turn), Constants.MessageType.TYPE_ERROR
                    )

            }
        }
    }

    inner class CardPlayedListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response CardPlayed $data")

                val responsePlayedCard =
                    Gson().fromJson(data.toString(), ResponseCardPlayedPOJO::class.java)

                if (responsePlayedCard.status!=200){
                    val messageResourceId: Int = when(responsePlayedCard.status){
                        Constants.CODE_NOT_YOUR_TURN->R.string.toast_warning_not_turn
                        Constants.CODE_PLAYER_OUT_OF_ROUND-> R.string.toast_player_out_of_round
                        Constants.CODE_PLAYER_PROTECTED -> R.string.toast_player_protected
                        Constants.CODE_CAN_NOT_PLAY_YOURSELF -> R.string.toast_can_not_play_yourself
                        Constants.CODE_WRONG_CARD_PLAYED -> R.string.toast_warning_discard_countess
                        Constants.CODE_ALL_PLAYERS_PROTECTED -> R.string.toast_warning_all_players_protected
                        Constants.CODE_PLAY_OTHER_CARD -> R.string.toast_warning_all_players_protected_play_other
                        else -> R.string.toast_error_server

                    }
                    mView.showMessage(
                        mView.getContextOfActivity()?.getString(messageResourceId),
                        Constants.MessageType.TYPE_WARNING
                    )
                }


            }
        }
    }

    inner class PlayerLostListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response PlayerLost $data")

//                val responsePlayerTurn =
//                    Gson().fromJson(data.toString(), ResponsePlayerTurn::class.java)
//
//                if (responsePlayerTurn.status == 200) {
//                    mView.makeTurnOfPlayer(responsePlayerTurn.playerId)
//                    mView.giveCardToPlayer(responsePlayerTurn.playerId)
//
//                } else
//                    mView.showMessage(
//                        mView.getContextOfActivity()
//                            ?.getString(R.string.toast_error_turn), Constants.MessageType.TYPE_ERROR
//                    )

            }
        }
    }

    inner class ActivePlayersListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response ActivePlayers $data")
                val responseActivePlayers =
                    Gson().fromJson(data.toString(), ResponsePlayersUpdatePojo::class.java)
                if (responseActivePlayers.status == 200)
                    mView.playersStateUpdated(responseActivePlayers.data)
            }
        }
    }

    inner class NewCardPlayedListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response NewCardPlayed $data")

                val responseNewCardPlayed =
                    Gson().fromJson(data.toString(), ResponseNewCardPlayedPOJO::class.java)
                println("Response Gson NewCardPlayed $responseNewCardPlayed")
                if (responseNewCardPlayed.status==200){

                    val playerId=responseNewCardPlayed.data.playerFrom
                    val cardType=responseNewCardPlayed.data.cardType
                    val targetPlayerId:String?=responseNewCardPlayed.data.playerTo
                    mView.newCardPlayed(playerId,cardType,targetPlayerId)
                    if (cardType==CardPojo.TYPE_KING){
                        Handler().postDelayed({mView.swapCards(playerId, targetPlayerId)},2500)
                    }

                }
                else{
                    when(responseNewCardPlayed.status){
                        Constants.CODE_NOT_YOUR_TURN-> Unit
                        Constants.CODE_PLAYER_OUT_OF_ROUND-> Unit
                        Constants.CODE_PLAYER_PROTECTED -> Unit
                        Constants.CODE_CAN_NOT_PLAY_YOURSELF -> Unit
                        Constants.CODE_WRONG_CARD_PLAYED -> Unit
                        Constants.CODE_ALL_PLAYERS_PROTECTED -> Unit
                        Constants.CODE_PLAY_OTHER_CARD -> Unit
                        else -> mView.showMessage(
                            mView.getContextOfActivity()?.getString(R.string.toast_error_server),
                            Constants.MessageType.TYPE_ERROR
                        )
                    }

                }

            }
        }
    }

    inner class KingPlayedListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response KindPlayed $data")
                val responseCardKing =
                    Gson().fromJson(data.toString(), ResponseKingPOJO::class.java)
                if (responseCardKing.status==200){
                    val firstPlayerId = responseCardKing.data.currentPlayerId
                    val secondPlayerId =responseCardKing.data.targetPlayerId
                    mView.swapCards(firstPlayerId, secondPlayerId)
                }
                else{
                    mView.showMessage(
                        mView.getContextOfActivity()?.getString(R.string.toast_error_server),
                        Constants.MessageType.TYPE_ERROR
                    )
                }

            }
        }
    }

    inner class PriestPlayedListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response Priest Played $data")
                val responseCardPriest =
                    Gson().fromJson(data.toString(), ResponsePriestPOJO::class.java)
                if (responseCardPriest.status == 200) {

                    if(responseCardPriest.data.cards!=null) {
                        val playerId = responseCardPriest.data.targetPlayerId
                        val cardType = responseCardPriest.data.cards[0].power
                        Handler().postDelayed(
                            { mView.lookOtherPlayerCard(playerId, cardType) },
                            2000

                        //0554446968
                        )
                    }

                } else
                    mView.showMessage(
                        mView.getContextOfActivity()?.getString(R.string.toast_error_server),
                        Constants.MessageType.TYPE_ERROR
                    )

            }
        }
    }

    inner class PrincePlayedListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                println("Response Prince played $data")
                val responseCardPrince =
                    Gson().fromJson(data.toString(), ResponsePrincePOJO::class.java)
//                if (responseActivePlayers.status == 200)
//                    mView.playersStateUpdated(responseActivePlayers.data)
            }
        }
    }
}