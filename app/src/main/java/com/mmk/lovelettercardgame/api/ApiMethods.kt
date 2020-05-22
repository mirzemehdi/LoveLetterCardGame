package com.mmk.lovelettercardgame.api

import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.pojo.ResponseRoomListPOJO
import com.mmk.lovelettercardgame.pojo.ResponseRoomPOJO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiMethods {

    interface RoomsService {
        @GET("rooms/")
        fun getRoomList(): Call<ResponseRoomListPOJO>

        @FormUrlEncoded
        @POST("rooms/")
        fun addRoom(
            @Field("roomName") roomName: String,
            @Field("maxPlayers") maxPlayers: String
        ): Call<ResponseAddRoomPojo>
    }
}