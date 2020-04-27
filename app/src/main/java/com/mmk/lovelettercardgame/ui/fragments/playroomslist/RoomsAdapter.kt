package com.mmk.lovelettercardgame.ui.fragments.playroomslist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.inflate
import com.mmk.lovelettercardgame.utils.viewholders.LoadingViewHolder

class RoomsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ROOM=1
    private val VIEW_TYPE_LOADING=0

    private val mRoomsList= mutableListOf<RoomPOJO?>()
    var roomItemClicked:((roomItem:RoomPOJO)->Unit)?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==VIEW_TYPE_ROOM)
            return RoomsItemViewHolder(
                parent.context,
                parent.inflate(R.layout.item_room),
                roomItemClicked
            )
        else
            return LoadingViewHolder(parent.inflate(R.layout.item_loading))

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
        else if (getItemViewType(position)==VIEW_TYPE_LOADING){
            holder as LoadingViewHolder
            holder.onBind()
        }
    }

    fun addRoomList(roomsList:List<RoomPOJO>){
        setLoading(false)
        val startPos=mRoomsList.size
        mRoomsList.addAll(roomsList)
        notifyItemRangeChanged(startPos,roomsList.size)
    }

    fun setLoading(isLoading:Boolean){
        if (isLoading){
            mRoomsList.add(null)
            notifyItemInserted(mRoomsList.size-1)
        }
        else{
            val lastPos=mRoomsList.size-1
            mRoomsList.removeAt(lastPos)
            notifyItemRemoved(lastPos)
        }
    }


    fun clearList () {
        mRoomsList.clear()
        notifyDataSetChanged()
    }
}