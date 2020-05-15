package com.mmk.lovelettercardgame.intractor

import android.util.EventLog
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.mmk.lovelettercardgame.api.ApiInitHelper
import com.mmk.lovelettercardgame.api.SocketInstance
import com.mmk.lovelettercardgame.pojo.CardPojo
import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.pojo.ResponseRoomListPOJO
import org.json.JSONObject
import retrofit2.Callback


class RoomsIntractor {
    private val EVENT_GET_ROOMS = "get-rooms"
    private val EVENT_RECEIVE_ROOMS = "receive-rooms"
    private val EVENT_NEW_ROOM = "new-room"
    private val EVENT_CREATED_ROOM = "created-room"
    private val EVENT_ENTER_ROOM = "enter-room"
    private val EVENT_ENTER_ROOM_RESPONSE = "room-response"
    private val EVENT_PLAYERS_RESPONSE = "update-room"
    private val EVENT_MY_CARDS = "my-cards"
    private val EVENT_PLAYER_TURN = "move-order"
    private val EVENT_PLAY_CARD = "make-turn"
    private val EVENT_PLAY_CARD_RESPONSE = "turn-result"
    private val EVENT_PLAYER_LOST = "player-lost"
    private val EVENT_CARD_PRIEST = "card-priest"
    private val EVENT_CARD_KING = "card-king"
    private val EVENT_CARD_PRINCE = "card-prince"
    private val EVENT_ACTIVE_PLAYERS = "active-players"
    private val EVENT_NEW_CARD_PLAYED = "played-card"


    private val eventsList = listOf(
        EVENT_GET_ROOMS,
        EVENT_RECEIVE_ROOMS,
        EVENT_NEW_ROOM,
        EVENT_CREATED_ROOM,
        EVENT_ENTER_ROOM,
        EVENT_ENTER_ROOM_RESPONSE,
        EVENT_PLAYERS_RESPONSE,
        EVENT_PLAYER_TURN,
        EVENT_PLAY_CARD,
        EVENT_PLAY_CARD_RESPONSE,
        EVENT_PLAYER_LOST,
        EVENT_ACTIVE_PLAYERS,
        EVENT_CARD_PRIEST,
        EVENT_CARD_KING,
        EVENT_CARD_PRINCE,
        EVENT_NEW_CARD_PLAYED

    )

    private val mSocket = SocketInstance.getInstance()

    init {
        println("RoomsIntractor called")
        mSocket?.let {
            if (!mSocket.connected())
                mSocket.connect()
        }


    }

    fun getRoomsList(listener: Emitter.Listener) {
        println("Rooms GetRoomList intractor")
        mSocket?.emit(EVENT_GET_ROOMS)
        mSocket?.on(EVENT_RECEIVE_ROOMS, listener)

    }

    fun addRoom(name: String, maxNBPlayers: String, listener: Emitter.Listener) {
        val roomJSONObject = JSONObject()
        roomJSONObject.put("roomName", name)
        roomJSONObject.put("maxPlayers", maxNBPlayers)

        mSocket?.emit(EVENT_NEW_ROOM, roomJSONObject)
        mSocket?.on(EVENT_CREATED_ROOM, listener)

    }

    fun joinRoom(playerName: String, roomId: String, listener: Emitter.Listener) {
        val roomEnterJSONObject = JSONObject()
        roomEnterJSONObject.put("roomId", roomId)
        roomEnterJSONObject.put("nickName", playerName)

        println("Join $roomId and $playerName")
        mSocket?.emit(EVENT_ENTER_ROOM, roomEnterJSONObject)
        mSocket?.on(EVENT_ENTER_ROOM_RESPONSE, listener)
    }

    fun getPlayers(listener: Emitter.Listener) {
        mSocket?.on(EVENT_PLAYERS_RESPONSE, listener)
    }

    fun getMyCards(listener: Emitter.Listener) {
        mSocket?.on(EVENT_MY_CARDS, listener)
    }

    fun listenActivePlayers(listener: Emitter.Listener) {
        mSocket?.on(EVENT_ACTIVE_PLAYERS, listener)
    }

    fun getPlayerTurn(listener: Emitter.Listener) {
        mSocket?.on(EVENT_PLAYER_TURN, listener)
    }

    fun playCard(
        cardPojo: CardPojo,
        targetPlayerId: String? = null,
        guessedCardType: String? = null
    ) {

        val cardJSONString = Gson().toJson(cardPojo)
        val relatedInfoJSONObject = JSONObject()
        relatedInfoJSONObject.put("targetPlayerId", targetPlayerId)
        relatedInfoJSONObject.put("guessedCardPower", guessedCardType)

        mSocket?.emit(EVENT_PLAY_CARD, JSONObject(cardJSONString), relatedInfoJSONObject)


        println("Card played ")
    }

    fun listenPlayedCard(listener: Emitter.Listener) {
        mSocket?.on(EVENT_PLAY_CARD_RESPONSE, listener)
    }

    fun listenPlayerLost(listener: Emitter.Listener) {
        mSocket?.on(EVENT_PLAYER_LOST, listener)
    }

    fun listenCardKing(listener: Emitter.Listener) {
        mSocket?.on(EVENT_CARD_KING,listener)
    }
    fun listenCardPrince(listener: Emitter.Listener) {
        mSocket?.on(EVENT_CARD_PRINCE,listener)
    }
    fun listenCardPriest(listener: Emitter.Listener) {
        mSocket?.on(EVENT_CARD_PRIEST,listener)
    }

    fun listernForNewCardPlayed(listener: Emitter.Listener){
        mSocket?.on(EVENT_NEW_CARD_PLAYED,listener)
    }


    fun closeServer() {
        mSocket?.disconnect()
        eventsList.forEach {
            mSocket?.off(it)
        }

    }


}


