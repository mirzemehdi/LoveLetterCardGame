package com.mmk.lovelettercardgame.ui.playrooms

import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView

interface RoomsContractor {

    interface View :BaseView<RoomsPresenter>{

        fun showRoomList(roomsList: List<RoomPOJO>)
    }

    interface Presenter{
        fun getRoomList()
        fun addRoom(room:RoomPOJO)
    }
}