package com.mmk.lovelettercardgame.ui.fragments.playroomslist

import android.os.Handler
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.RoomPOJO

class RoomsPresenter(private val mView: RoomsContractor.View):
    RoomsContractor.Presenter {
    private val roomsIntractor=RoomsIntractor()

    init {
        mView.setPresenter(this)
    }

    override fun getRoomList() {
        mView.showItemLoading(true)
        Handler().postDelayed(Runnable {
            mView.showRoomList(roomsIntractor.roomsList)
        },2000)
//        mView.showRoomList(roomsIntractor.roomsList)

    }

    override fun addRoom(room: RoomPOJO) {
        roomsIntractor.addRoom(room)
    }


}