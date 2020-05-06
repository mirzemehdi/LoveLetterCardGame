package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CardPojo(

    @Expose
    @SerializedName("id")
    val id:String="",

    @Expose
    @SerializedName("name")
    val name:String="",

    @Expose
    @SerializedName("power")
    val power:Int,

    @Expose
    @SerializedName("playerID")
    val playerID:String=""


)