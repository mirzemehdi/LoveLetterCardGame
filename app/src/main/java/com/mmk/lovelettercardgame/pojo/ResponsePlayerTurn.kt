package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponsePlayerTurn(

    @Expose
    @SerializedName("status")
    val status:Int,

    @Expose
    @SerializedName("message")
    val message:String,

    @Expose
    @SerializedName("data")
    val playerId:String

) {
}