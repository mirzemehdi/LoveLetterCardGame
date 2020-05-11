package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlayerPOJO (
    @Expose
    @SerializedName("id")
    val id:String,
    @Expose
    @SerializedName("nickname")
    val name:String,
    @Expose
    @SerializedName("points")
    val points:String="",
    @Expose
    @SerializedName("socketId")
    val socketId: String ="",
    @Expose
    @SerializedName("isProtected")
    var isProtected:Boolean=false,
    @Expose
    @SerializedName("cards")
    var cards:List<CardPojo> = listOf(),
    @Expose
    @SerializedName("discardedCards")
    var discardedCards:List<CardPojo> = listOf()


):Serializable {

    var cardsCount=0


}