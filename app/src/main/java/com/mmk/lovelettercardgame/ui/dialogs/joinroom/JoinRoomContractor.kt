package com.mmk.lovelettercardgame.ui.dialogs.joinroom

import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.ui.BaseView
import com.mmk.lovelettercardgame.ui.dialogs.addroom.AddRoomPresenter
import com.mmk.lovelettercardgame.utils.Constants

interface JoinRoomContractor {

    interface View : BaseView<JoinRoomPresenter> {
        fun showMessage(message:String?,messageType: Constants.MessageType)
        fun playerIsJoined(playerPOJO: PlayerPOJO)
    }

    interface Presenter{
        fun joinRoom(playerName:String,roomId:String)
    }
}