package com.mmk.lovelettercardgame.ui.fragments.playroomslist

import android.os.Handler
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.pojo.ResponseRoomPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NumberFormatException

class RoomsPresenter(private val mView: RoomsContractor.View):
    RoomsContractor.Presenter {
    private val roomsIntractor=RoomsIntractor()

    init {
        mView.setPresenter(this)
    }

    override fun getRoomList() {
        mView.showItemLoading(true)
        roomsIntractor.getRoomsList(RoomListCallBack())
    }

    override fun addRoom(room: RoomPOJO) {
        //roomsIntractor.addRoom(room)
    }

    inner class RoomListCallBack : Callback<List<ResponseRoomPOJO>>{

        override fun onResponse(
            call: Call<List<ResponseRoomPOJO>>,
            response: Response<List<ResponseRoomPOJO>>
        ) {
            if (response.isSuccessful ) {

                    val roomList: List<RoomPOJO>? = response.body()?.map {
                        var playersNumber: Int? = try {
                            it.players?.toInt()
                        } catch (e:NumberFormatException){
                            0
                        }
                        RoomPOJO(it.name?:"", playersNumber?:0)
                    }
                    mView.showRoomList(roomList?: mutableListOf())

            }
            else{
                mView.showItemLoading(false)
                errorMessage(R.string.toast_error_server)
            }
        }

        override fun onFailure(call: Call<List<ResponseRoomPOJO>>, t: Throwable) {
            mView.showItemLoading(false)
            errorMessage(R.string.toast_error_server)
        }


    }



    private fun errorMessage(messageResourceId:Int){
        val message=mView.getContextOfActivity()?.resources?.getString(messageResourceId)?:""
        mView.showMessage(message,Constants.MessageType.TYPE_ERROR)
    }
}