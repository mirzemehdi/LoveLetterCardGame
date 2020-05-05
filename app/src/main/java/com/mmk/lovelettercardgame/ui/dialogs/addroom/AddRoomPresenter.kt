package com.mmk.lovelettercardgame.ui.dialogs.addroom

import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRoomPresenter (private val mView: AddRoomContractor.View):
    AddRoomContractor.Presenter {
    private val roomsIntractor=RoomsIntractor()
    init {
        mView.setPresenter(this)
    }


    override fun addRoom(roomName: String, maxNbPlayers: String) {
        roomsIntractor.addRoom(roomName,maxNbPlayers,AddRoomListener())

    }


    inner class AddRoomListener: Emitter.Listener{
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data=args[0] as JSONObject
                println("AddedRoomResponse $data")
                val responseAddRoom=Gson().fromJson(data.toString(),ResponseAddRoomPojo::class.java)
                if (responseAddRoom.status==200){
                    mView.showMessage(
                        mView.getContextOfActivity()
                            ?.getString(R.string.toast_room_added_successfully)
                        , Constants.MessageType.TYPE_SUCCESS)
                    val newRoomResponseRoomPOJO=responseAddRoom.data
                    val newRoomPojo=RoomPOJO(newRoomResponseRoomPOJO.id,
                        newRoomResponseRoomPOJO.name,
                        newRoomResponseRoomPOJO.players,
                        newRoomResponseRoomPOJO.status,
                        newRoomResponseRoomPOJO.maxPlayers?.toInt())
                    mView.newRoomIsAdded(newRoomPojo)
                }
                else{
                    mView.showMessage(mView.getContextOfActivity()
                        ?.getString(R.string.toast_error_add_room)
                        ,Constants.MessageType.TYPE_ERROR)
                }
            }
        }
    }

}