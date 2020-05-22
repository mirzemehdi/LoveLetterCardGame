package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseRoomPOJO(
    @Expose
    @SerializedName("id")
    val id:String,
    @Expose
    @SerializedName("name")
    val name:String?="",
    @Expose
    @SerializedName("players")
    val players:List<PlayerPOJO>,
    @Expose
    @SerializedName("status")
    val status:String?="",
    @Expose
    @SerializedName("maxPlayers")
    val maxPlayers:String?=""


)