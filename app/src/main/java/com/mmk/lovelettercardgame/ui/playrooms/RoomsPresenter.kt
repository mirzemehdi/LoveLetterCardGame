package com.mmk.lovelettercardgame.ui.playrooms

import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.RoomPOJO

class RoomsPresenter(private val mView:RoomsContractor.View):RoomsContractor.Presenter {
    private val roomsIntractor=RoomsIntractor()

    init {
        mView.setPresenter(this)
    }

    override fun getRoomList() {
        mView.showRoomList(roomsIntractor.roomsList)

    }

    override fun addRoom(room: RoomPOJO) {
        roomsIntractor.addRoom(room)
    }


}