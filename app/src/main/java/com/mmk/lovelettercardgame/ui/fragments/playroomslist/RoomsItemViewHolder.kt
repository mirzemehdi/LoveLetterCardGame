package com.mmk.lovelettercardgame.ui.fragments.playroomslist

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import kotlinx.android.synthetic.main.item_room.view.*

class RoomsItemViewHolder(val context: Context,itemView: View
                          ,val roomItemClicked:((roomItem:RoomPOJO)->Unit)?)
    : RecyclerView.ViewHolder(itemView) {
    private val roomNameTextView:TextView
    private val nbPlayersTextView:TextView
    private val membersNameTextView:TextView

    init {
        itemView.apply {
            roomNameTextView=text_view_item_room_name
            nbPlayersTextView=text_view_item_room_number_players
            membersNameTextView=text_view_item_room_member_names
        }
    }
    fun onBind(roomItem:RoomPOJO){
        roomNameTextView.text=roomItem.name
        nbPlayersTextView.text=context
            .getString(R.string.rooms_nb_players_slash_max_players,
            roomItem.getNbCurrentPlayers(),roomItem.maxNbPlayers)

        var playersName=""
        roomItem.players.forEach{player->
            playersName+=player.name+","
        }
        membersNameTextView.text=context.getString(R.string.rooms_members_name,playersName)

        itemView.setOnClickListener { roomItemClicked?.invoke(roomItem) }
    }
}