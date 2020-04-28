package com.mmk.lovelettercardgame.ui.fragments.playroomslist

import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView

interface RoomsContractor {

    interface View :BaseView<RoomsPresenter>{

        fun showRoomList(roomsList: List<RoomPOJO>)
        fun showItemLoading(isLoading:Boolean)
        fun showErrorMessage(message:String)
    }

    interface Presenter{
        fun getRoomList()
        fun addRoom(room:RoomPOJO)
    }
}