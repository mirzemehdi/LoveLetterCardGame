package com.mmk.lovelettercardgame.ui.dialogs

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRoomPresenter (private val mView:AddRoomContractor.View):AddRoomContractor.Presenter {
    val roomsIntractor=RoomsIntractor()
    init {
        mView.setPresenter(this)
    }


    override fun addRoom(roomName: String, maxNbPlayers: String) {
        roomsIntractor.addRoom(roomName,maxNbPlayers,AddRoomCallBack())

    }



    inner class AddRoomCallBack :Callback<ResponseBody>{


        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            mView.showMessage(mView.getContextOfActivity()
                ?.getString(R.string.toast_room_added_successfully)
                ,Constants.MessageType.TYPE_SUCCESS)
            mView.newRoomIsAdded()
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            mView.showMessage(mView.getContextOfActivity()
                ?.getString(R.string.toast_error_server)
                ,Constants.MessageType.TYPE_ERROR)
        }

    }
}