package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponsePriestPOJO(

    @Expose
    @SerializedName("status")
    val status:Int,

    @Expose
    @SerializedName("message")
    val message:String,

    @Expose
    @SerializedName("data")
    val data:PriestData
){
    inner class PriestData(
        @Expose
        @SerializedName("targetPlayerId")
        val targetPlayerId:String,

        @Expose
        @SerializedName("cardResponseResult")
        val cards:List<CardPojo>

    )
}