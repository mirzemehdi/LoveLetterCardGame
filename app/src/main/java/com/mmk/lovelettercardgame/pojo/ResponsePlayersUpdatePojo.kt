package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponsePlayersUpdatePojo(
    @Expose
    @SerializedName("status")
    val status:Int,

    @Expose
    @SerializedName("message")
    val message:String,

    @Expose
    @SerializedName("data")
    val data:List<PlayerPOJO>
)