package com.mmk.lovelettercardgame.ui.fragments.game

import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView

interface GameContractor {

    interface View:BaseView<GamePresenter>{

        fun showPlayers(players:List<PlayerPOJO>)
        fun giveCardToPlayer(playerPOJO: PlayerPOJO?)
        fun giveCardToAllPlayers(players: List<PlayerPOJO>?)
        fun hideShowWaitingText(isWaitingPlayers:Boolean)
        fun saveMyOwnPlayer(playerPOJO: PlayerPOJO)
        fun makeTurnOfPlayer(playerPOJO: PlayerPOJO?)
    }

    interface Presenter{

        fun getPlayers(roomItem:RoomPOJO?)
        fun joinGame(playerPOJO: PlayerPOJO)
        fun listerForPlayersUpdate()
    }
}