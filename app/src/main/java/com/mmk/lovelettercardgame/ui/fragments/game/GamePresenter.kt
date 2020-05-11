package com.mmk.lovelettercardgame.ui.fragments.game

import android.os.Handler
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.*
import com.mmk.lovelettercardgame.utils.Constants
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import org.json.JSONObject

class GamePresenter(private val mView: GameContractor.View) : GameContractor.Presenter {
    private val roomsIntractor = RoomsIntractor()

    init {
        mView.setPresenter(this)
    }

    val playersList = mutableListOf(
        PlayerPOJO("1", "Anday")
        , PlayerPOJO("2", "Sirin")
        , PlayerPOJO("3", "Amine")
        , PlayerPOJO("4", "Ramin")
    )

    override fun getPlayers(roomItem: RoomPOJO?) {
        listenForPlayersUpdate(roomItem?.maxNbPlayers)
        getMyCards()
        listenForTurn()
        mView.showPlayers(roomItem?.players ?: mutableListOf())

        //TODO REMOVE THESE 2 LINES
//        mView.showPlayers(playersList)
//        for (i in 1..2) {
//            mView.addToDiscardedCard("1", i)
//        }
//
//        startGame(playersList)


    }

    private fun listenForTurn() {
        roomsIntractor.getPlayerTurn(PlayerTurnListener())
    }

    override fun joinGame(playerPOJO: PlayerPOJO) {
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

    override fun playCard(cardPojo: CardPojo, targetPlayerId: String?, guessedCardType: String?) {
        roomsIntractor.playCard(cardPojo, targetPlayerId, guessedCardType,CardPlayedListener())
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
}