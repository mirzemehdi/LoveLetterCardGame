package com.mmk.lovelettercardgame.ui.dialogs.joinroom

import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.pojo.ResponseJoinRoomPojo
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.Constants
import org.json.JSONObject

class JoinRoomPresenter(private val mView:JoinRoomContractor.View):JoinRoomContractor.Presenter {
    private val roomsIntractor= RoomsIntractor()


    init {
        mView.setPresenter(this)
    }

    override fun joinRoom(playerName: String,roomId:String) {
       roomsIntractor.joinRoom(playerName,roomId,JoinRoomListener())
//        mView.playerIsJoined(player)
    }




    inner class JoinRoomListener: Emitter.Listener{
        override fun call(vararg args: Any?) {
            mView.getActivityOfActivity()?.runOnUiThread {
                val data=args[0] as JSONObject
                println("JoinedRoomResponse $data")
                val responseJoinRoom= Gson().fromJson(data.toString(), ResponseJoinRoomPojo::class.java)
                if (responseJoinRoom.status==200){

                    mView.playerIsJoined(responseJoinRoom.data)
                }
                else{
                    mView.showMessage(mView.getContextOfActivity()
                        ?.getString(R.string.toast_error_join_room)
                        , Constants.MessageType.TYPE_ERROR)
                }
            }
        }
    }
}