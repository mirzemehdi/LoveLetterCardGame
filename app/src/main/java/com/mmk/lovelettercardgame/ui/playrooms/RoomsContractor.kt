package com.mmk.lovelettercardgame.ui.playrooms

import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView

interface RoomsContractor {

    interface View :BaseView<RoomsPresenter>{

        fun showRoomList(roomsList: List<RoomPOJO>)
    }

    interface Presenter{
        fun getRoomList():List<RoomPOJO>
        fun addRoom(room:RoomPOJO)
    }
}