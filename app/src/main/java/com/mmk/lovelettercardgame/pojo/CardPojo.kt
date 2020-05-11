package com.mmk.lovelettercardgame.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CardPojo(


    @Expose
    @SerializedName("id")
    val id: String = "",

    @Expose
    @SerializedName("name")
    val name: String = "",

    @Expose
    @SerializedName("power")
    val power: Int,

    @Expose
    @SerializedName("playerID")
    val playerID: String = ""

):Serializable {
    companion object {

        const val TYPE_GUARD = 1
        const val TYPE_PRIEST = 2
        const val TYPE_BARON = 3
        const val TYPE_HANDMAID = 4
        const val TYPE_PRINCE = 5
        const val TYPE_KING = 6
        const val TYPE_COUNTESS = 7
        const val TYPE_PRINCESS = 8

    }
}