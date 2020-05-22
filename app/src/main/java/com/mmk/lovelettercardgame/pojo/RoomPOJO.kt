package com.mmk.lovelettercardgame.pojo

import java.io.Serializable

data class RoomPOJO(val name: String = "", val maxNbPlayers: Int) : Serializable {
    var id: String? = ""
    var players: List<PlayerPOJO> = listOf()
    var status = ""

    constructor(
        id: String?,
        name: String?,
        players: List<PlayerPOJO>?,
        status:String?,
        maxNbPlayers: Int?
    ) : this(name?:"", maxNbPlayers?:0) {
        this.id = id
        this.players = players?: mutableListOf()
        this.status=status?:""
    }


    fun getNbCurrentPlayers() = players.size

}