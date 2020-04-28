package com.mmk.lovelettercardgame.pojo

import java.io.Serializable

data class RoomPOJO(val name:String="",val maxNbPlayers:Int) :Serializable{
    val players: MutableList<PlayerPOJO> = mutableListOf()

    fun getNbCurrentPlayers()=players.size

}