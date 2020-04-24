package com.mmk.lovelettercardgame.ui.playrooms

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.inflate

class RoomsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ROOM=1
    private val VIEW_TYPE_LOADING=0

    private val mRoomsList= mutableListOf<RoomPOJO?>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //if (viewType==VIEW_TYPE_ROOM)
            return RoomsItemViewHolder(parent.context,parent.inflate(R.layout.item_room))

    }

    override fun getItemCount(): Int =mRoomsList.size
    override fun getItemViewType(position: Int): Int {

        if (mRoomsList[position]!=null)
            return VIEW_TYPE_ROOM
        else
            return VIEW_TYPE_LOADING
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)==VIEW_TYPE_ROOM){
            holder as RoomsItemViewHolder
            holder.onBind(mRoomsList[position]!!)
        }
    }

    fun addRoomList(roomsList:List<RoomPOJO>){
        val startPos=mRoomsList.size
        mRoomsList.addAll(roomsList)
        notifyItemRangeChanged(startPos,roomsList.size)
    }

    fun clearList () {
        mRoomsList.clear()
        notifyDataSetChanged()
    }
}