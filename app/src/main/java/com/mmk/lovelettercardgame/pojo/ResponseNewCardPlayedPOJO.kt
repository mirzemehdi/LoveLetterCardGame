package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseNewCardPlayedPOJO(
    @Expose
    @SerializedName("status")
    val status:Int,

    @Expose
    @SerializedName("message")
    val message:String,

    @Expose
    @SerializedName("data")
    val data:NewCardPlayedData



):Serializable{

    inner class NewCardPlayedData(


        @Expose
        @SerializedName("fromPlayer")
        val playerFrom:String,
        @Expose
        @SerializedName("cardPower")
        val cardType:Int,
        @Expose
        @SerializedName("toPlayer")
        val playerTo:String?

    )
}