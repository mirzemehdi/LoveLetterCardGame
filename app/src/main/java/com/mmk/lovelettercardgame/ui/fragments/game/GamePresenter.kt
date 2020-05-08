package com.mmk.lovelettercardgame.ui.fragments.game

import android.os.Handler
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.*
import com.mmk.lovelettercardgame.utils.Constants
import com.mmk.lovelettercardgame.utils.animations.CardAnimations
import org.json.JSONObject

class GamePresenter(private val mView: GameContractor.View) : GameContractor.Presenter {
    private val roomsIntractor= RoomsIntractor()
    init {
        mView.setPresenter(this)
    }

    val playersList = mutableListOf(
        PlayerPOJO("1", "Anday")
        , PlayerPOJO("2", "Sirin")
        , PlayerPOJO("3", "Amine")
        ,PlayerPOJO("4","Ramin")
    )

    override fun getPlayers(roomItem:RoomPOJO?) {
        listenForPlayersUpdate(roomItem?.maxNbPlayers)
        getMyCards()
        mView.showPlayers(roomItem?.players?: mutableListOf())

        //TODO REMOVE THESE 2 LINES
        mView.showPlayers(playersList)
        startGame(playersList)


    }

    override fun joinGame(playerPOJO: PlayerPOJO) {
        mView.saveMyOwnPlayer(playerPOJO)

    }

    override  fun listenForPlayersUpdate(maxNbPlayers: Int?) {
        println("PlayersUpdateResponse listen")
        roomsIntractor.getPlayers(PlayersUpdateListener(maxNbPlayers))
    }

    override fun getMyCards() {
        roomsIntractor.getMyCards(MyCardsUpdateListener())
    }

    private fun startGame(players:List<PlayerPOJO>){
        mView.giveCardToAllPlayers(players)
        mView.hideShowWaitingText(false)
        Handler().postDelayed(Runnable {
            mView.giveCardToPlayer(players[0])
            mView.makeTurnOfPlayer(players[0])




        }
            ,
            players.size * (CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION + CardAnimations.DURATION_DEAL_CARD_ANIMATION))



    }

    inner class PlayersUpdateListener(val maxNbPlayers: Int?) : Emitter.Listener{
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data=args[0] as JSONObject
                println("PlayersUpdateResponse $data")
                val responsePlayersUpdate= Gson().fromJson(data.toString(), ResponsePlayersUpdatePojo::class.java)
                if (responsePlayersUpdate.status==200){
                    mView.showPlayers(responsePlayersUpdate.data)

                    val players=responsePlayersUpdate.data
                    if (players.size == maxNbPlayers) {
                        startGame(players)
                    }

                }

            }
        }
    }

    inner class MyCardsUpdateListener : Emitter.Listener{
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data=args[0] as JSONObject
                println("CardsUpdateResponse $data")
                val responseMyCards= Gson().fromJson(data.toString(), ResponseCardPOJO::class.java)
                if (responseMyCards.status==200){
                    mView.myCardsUpdated(responseMyCards.data)
                }
                else
                    mView.showMessage(mView.getContextOfActivity()
                        ?.getString(R.string.toast_error_my_cards),Constants.MessageType.TYPE_WARNING)

            }
        }
    }
}