package com.mmk.lovelettercardgame.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.mmk.lovelettercardgame.R

class AddRoomDialog(context: Context?) {

    private val dialog :Dialog = Dialog(context!!)


    init {
        dialog.setContentView(R.layout.user_box_view)
    }


    fun show(){
        dialog.show()
    }

}