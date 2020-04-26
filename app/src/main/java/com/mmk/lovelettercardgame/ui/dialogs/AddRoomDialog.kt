package com.mmk.lovelettercardgame.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.utils.toast
import kotlinx.android.synthetic.main.dialog_add_room.view.*

class AddRoomDialog(private val context: Context?) {

    private val dialog :Dialog = Dialog(context!!)
    private val view: View
    private val roomNameEditText:AppCompatEditText
    private val maxPlayersRadioGroup:RadioGroup
    private val addRoomButton:Button



    init {
        view=LayoutInflater.from(context).inflate(R.layout.dialog_add_room,null)
        dialog.setContentView(view)
        dialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        roomNameEditText=view.edit_text_add_room_room_name
        maxPlayersRadioGroup=view.radio_group_add_room_max_players
        addRoomButton=view.button_dialog_add_room_add
        setClicks()


    }

    private fun setClicks() {
        addRoomButton.setOnClickListener {
            val selectedRadioButton:RadioButton=view.findViewById(maxPlayersRadioGroup.checkedRadioButtonId)

            val roomName=roomNameEditText.text
            val maxPlayersNumber=selectedRadioButton.text
            context?.toast("Added new Room: $roomName and $maxPlayersNumber")
        }
    }


    fun show(){
        dialog.show()
    }

}