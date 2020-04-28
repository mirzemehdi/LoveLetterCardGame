package com.mmk.lovelettercardgame.intractor

import com.mmk.lovelettercardgame.api.ApiInitHelper
import com.mmk.lovelettercardgame.pojo.ResponseRoomPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import retrofit2.Callback

class RoomsIntractor {
    private val apiInitHelper=ApiInitHelper.defaultApi


    fun getRoomsList(callback: Callback<List<ResponseRoomPOJO>>){
        apiInitHelper
            .roomsService
            .getRoomList()
            .enqueue(callback)
    }

    fun addRoom(room:RoomPOJO){
        //roomsList.add(room)

    }
}