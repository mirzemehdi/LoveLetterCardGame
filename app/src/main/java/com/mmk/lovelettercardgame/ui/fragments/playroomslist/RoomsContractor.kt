package com.mmk.lovelettercardgame.ui.fragments.playroomslist

import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView
import com.mmk.lovelettercardgame.utils.Constants

interface RoomsContractor {

    interface View :BaseView<RoomsPresenter>{

        fun showRoomList(roomsList: List<RoomPOJO>)
        fun showItemLoading(isLoading:Boolean)
        fun showMessage(message:String,type:Constants.MessageType)
    }

    interface Presenter{
        fun getRoomList()

    }
}