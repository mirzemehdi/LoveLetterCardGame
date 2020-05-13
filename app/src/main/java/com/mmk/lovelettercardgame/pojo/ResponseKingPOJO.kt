package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseKingPOJO(

    @Expose
    @SerializedName("status")
    val status:Int,

    @Expose
    @SerializedName("message")
    val message:String,

    @Expose
    @SerializedName("data")
    val data:KingData
){

    inner class KingData(
        @Expose
        @SerializedName("currentPlayerId")
        val currentPlayerId:String,

        @Expose
        @SerializedName("targetPlayerId")
        val targetPlayerId:String
    )
}