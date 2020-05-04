package com.mmk.lovelettercardgame.ui.dialogs.joinroom

import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.PlayerPOJO

class JoinRoomPresenter(private val mView:JoinRoomContractor.View):JoinRoomContractor.Presenter {
    private val roomsIntractor= RoomsIntractor()


    init {
        mView.setPresenter(this)
    }

    override fun joinPlayer(playerName: String) {
       roomsIntractor.joinRoom(playerName)
        val player=PlayerPOJO("5",playerName)
        mView.playerIsJoined(player)
    }
}