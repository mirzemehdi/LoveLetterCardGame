package com.mmk.lovelettercardgame.intractor

import com.mmk.lovelettercardgame.api.ApiInitHelper
import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.pojo.ResponseRoomListPOJO
import com.mmk.lovelettercardgame.pojo.ResponseRoomPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomsIntractor {
    private val apiInitHelper=ApiInitHelper.defaultApi


    fun getRoomsList(callback: Callback<ResponseRoomListPOJO>){
        apiInitHelper
            .roomsService
            .getRoomList()
            .enqueue(callback)
    }

    fun addRoom(name:String,maxNBPlayers:String,callback: Callback<ResponseAddRoomPojo>){
        apiInitHelper
            .roomsService
            .addRoom(name,maxNBPlayers)
            .enqueue(callback)

    }

    fun joinRoom(playerName:String){

    }


}
