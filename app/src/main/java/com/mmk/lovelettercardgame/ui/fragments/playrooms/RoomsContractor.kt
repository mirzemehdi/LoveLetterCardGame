package com.mmk.lovelettercardgame.ui.fragments.playrooms

import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView

interface RoomsContractor {

    interface View :BaseView<RoomsPresenter>{

        fun showRoomList(roomsList: List<RoomPOJO>)
        fun showItemLoading(isLoading:Boolean)
    }

    interface Presenter{
        fun getRoomList()
        fun addRoom(room:RoomPOJO)
    }
}