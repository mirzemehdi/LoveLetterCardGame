package com.mmk.lovelettercardgame.ui.fragments.game

import android.os.Handler
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.pojo.ResponseJoinRoomPojo
import com.mmk.lovelettercardgame.pojo.ResponsePlayersUpdatePojo
import com.mmk.lovelettercardgame.pojo.RoomPOJO
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
    )

    override fun getPlayers(roomItem:RoomPOJO?) {

        mView.showPlayers(roomItem?.players?: mutableListOf())
//        if (playersList.size == 4) {
//            mView.giveCardToAllPlayers(playersList)
//            mView.hideShowWaitingText(false)
//            Handler().postDelayed(Runnable {
//                mView.giveCardToPlayer(playersList[3])
//                mView.makeTurnOfPlayer(playersList[3])
//            }
//                ,
//                4 * (CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION + CardAnimations.DURATION_DEAL_CARD_ANIMATION))
//
//            Handler().postDelayed({
//                mView.makeTurnOfPlayer(playersList[0])
//            },6000)
//        }

    }

    override fun joinGame(playerPOJO: PlayerPOJO) {
        mView.saveMyOwnPlayer(playerPOJO)
        //listerForPlayersUpdate()
    }

    override fun listerForPlayersUpdate() {
        println("PlayersUpdateResponse listen")

        roomsIntractor.getPlayers(PlayersUpdateListener())
    }


    inner class PlayersUpdateListener: Emitter.Listener{
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data=args[0] as JSONObject
                println("PlayersUpdateResponse $data")
                val responsePlayersUpdate= Gson().fromJson(data.toString(), ResponsePlayersUpdatePojo::class.java)
                if (responsePlayersUpdate.status==200){
                    mView.showPlayers(responsePlayersUpdate.data)
                }

            }
        }
    }
}