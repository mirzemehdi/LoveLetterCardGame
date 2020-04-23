package com.mmk.lovelettercardgame.pojo

class RoomPOJO(val name:String,val maxNbPlayers:Int) {
    val players: MutableList<PlayerPOJO> = mutableListOf()

    fun getNbCurrentPlayers()=players.size

}