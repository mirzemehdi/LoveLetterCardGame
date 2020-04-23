package com.mmk.lovelettercardgame.intractor

import com.mmk.lovelettercardgame.pojo.RoomPOJO

class RoomsIntractor {
    val roomsList= mutableListOf<RoomPOJO>()

    init {
        for (i in 0 until 5){
            roomsList.add(RoomPOJO("Room $i",(1..4).random()))
        }
    }

    fun addRoom(room:RoomPOJO){
        roomsList.add(room)
    }
}