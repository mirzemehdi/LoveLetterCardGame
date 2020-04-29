package com.mmk.lovelettercardgame.ui.dialogs

import android.content.Context
import com.mmk.lovelettercardgame.ui.BaseView
import com.mmk.lovelettercardgame.utils.Constants

interface AddRoomContractor {

    interface View :BaseView<AddRoomPresenter>{
        fun showMessage(message:String?,messageType:Constants.MessageType)
        fun newRoomIsAdded()
    }

    interface Presenter{
        fun addRoom(roomName:String,maxNbPlayers:String)
    }
}