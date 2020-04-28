package com.mmk.lovelettercardgame.api

import com.mmk.lovelettercardgame.pojo.ResponseRoomPOJO
import retrofit2.Call
import retrofit2.http.GET

interface ApiMethods {

    interface RoomsService{
        @GET("rooms/")
        fun getRoomList():Call<List<ResponseRoomPOJO>>
    }
}