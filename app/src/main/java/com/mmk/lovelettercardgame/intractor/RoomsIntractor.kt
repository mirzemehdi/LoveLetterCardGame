package com.mmk.lovelettercardgame.intractor

import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.mmk.lovelettercardgame.api.ApiInitHelper
import com.mmk.lovelettercardgame.api.SocketInstance
import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.pojo.ResponseRoomListPOJO
import org.json.JSONObject
import retrofit2.Callback


class RoomsIntractor {
    private val EVENT_GET_ROOMS="get-rooms"
    private val EVENT_RECEIVE_ROOMS="receive-rooms"
    private val EVENT_ADD_ROOM="add-room"




    private val mSocket=SocketInstance.getInstance()
    init {
        mSocket?.connect()
    }

    fun getRoomsList(listener: Emitter.Listener){

        mSocket?.emit(EVENT_GET_ROOMS)
        mSocket?.on(EVENT_RECEIVE_ROOMS,listener)

    }

    fun addRoom(name:String,maxNBPlayers:String,listener: Emitter.Listener){
        mSocket?.emit(EVENT_ADD_ROOM,name,maxNBPlayers,listener)
    }

    fun joinRoom(playerName:String){
        println(playerName)
    }

    fun closeServer(){
        mSocket?.disconnect()
        mSocket?.off("get-rooms")
        mSocket?.off("receive-rooms")
    }







}


