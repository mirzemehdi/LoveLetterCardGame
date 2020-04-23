package com.mmk.lovelettercardgame.ui.playrooms

import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.RoomPOJO

class RoomsPresenter:RoomsContractor.Presenter {
    private val roomsIntractor=RoomsIntractor()

    override fun getRoomList(): List<RoomPOJO> {
        return roomsIntractor.roomsList
    }

    override fun addRoom(room: RoomPOJO) {
        roomsIntractor.addRoom(room)
    }


}