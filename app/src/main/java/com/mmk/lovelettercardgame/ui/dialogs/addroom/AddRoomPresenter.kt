package com.mmk.lovelettercardgame.ui.dialogs.addroom

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.utils.Constants
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
        roomsIntractor.addRoom(roomName,maxNbPlayers,AddRoomCallBack())

    }



    inner class AddRoomCallBack :Callback<ResponseAddRoomPojo>{


        override fun onResponse(call: Call<ResponseAddRoomPojo>, response: Response<ResponseAddRoomPojo>) {

            if (response.isSuccessful&&response.body()!=null&&response.body()?.status==200) {
                mView.showMessage(
                    mView.getContextOfActivity()
                        ?.getString(R.string.toast_room_added_successfully)
                    , Constants.MessageType.TYPE_SUCCESS
                )
                mView.newRoomIsAdded()
            }
            else{
                mView.showMessage(mView.getContextOfActivity()
                    ?.getString(R.string.toast_error_add_room)
                    ,Constants.MessageType.TYPE_ERROR)
            }
        }

        override fun onFailure(call: Call<ResponseAddRoomPojo>, t: Throwable) {
            mView.showMessage(mView.getContextOfActivity()
                ?.getString(R.string.toast_error_add_room)
                ,Constants.MessageType.TYPE_ERROR)
        }

    }
}