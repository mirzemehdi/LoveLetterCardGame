package com.mmk.lovelettercardgame.ui.dialogs.addroom

import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView
import com.mmk.lovelettercardgame.ui.dialogs.addroom.AddRoomPresenter
import com.mmk.lovelettercardgame.utils.Constants

interface AddRoomContractor {

    interface View :BaseView<AddRoomPresenter>{
        fun showMessage(message:String?,messageType:Constants.MessageType)
        fun newRoomIsAdded(roomPOJO: RoomPOJO)
    }

    interface Presenter{
        fun addRoom(roomName:String,maxNbPlayers:String)
    }
}