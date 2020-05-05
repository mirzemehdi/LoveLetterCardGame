package com.mmk.lovelettercardgame.ui.fragments.playroomslist

import android.os.Handler
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.ResponseRoomListPOJO
import com.mmk.lovelettercardgame.pojo.ResponseRoomPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NumberFormatException

class RoomsPresenter(private val mView: RoomsContractor.View) :
    RoomsContractor.Presenter {
    private val roomsIntractor = RoomsIntractor

    init {
        mView.setPresenter(this)
    }

    override fun getRoomList() {
        mView.showItemLoading(true)
        roomsIntractor.getRoomsList(RoomListListener())
    }


    inner class RoomListListener : Emitter.Listener {
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {

                val data = args[0] as JSONObject
                println("Rooms response: $data")
                val gson = Gson()
                val roomListResponse =
                    gson.fromJson(data.toString(), ResponseRoomListPOJO::class.java)
                if (roomListResponse.status == 200) {

                    val roomList: List<RoomPOJO>? = roomListResponse.data.map {
                        println("Rooms $it")
                        val newRoomResponseRoomPOJO=it
                        RoomPOJO(newRoomResponseRoomPOJO.id,
                            newRoomResponseRoomPOJO.name,
                            newRoomResponseRoomPOJO.players,
                            newRoomResponseRoomPOJO.status,
                            newRoomResponseRoomPOJO.maxPlayers?.toInt())
                    }
                    mView.showRoomList(roomList ?: mutableListOf())
                    mView.showItemLoading(false)
                } else {
                    mView.showItemLoading(false)

                }

            }
        }


    }




    private fun errorMessage(messageResourceId: Int) {
        val message = mView.getContextOfActivity()?.resources?.getString(messageResourceId) ?: ""
        mView.showMessage(message, Constants.MessageType.TYPE_ERROR)
    }
}