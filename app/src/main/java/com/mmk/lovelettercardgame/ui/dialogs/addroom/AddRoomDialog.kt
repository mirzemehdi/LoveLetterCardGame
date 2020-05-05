package com.mmk.lovelettercardgame.ui.dialogs.addroom

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.os.bundleOf
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.activities.MainActivity
import com.mmk.lovelettercardgame.ui.fragments.game.GameFragment
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.Constants
import com.mmk.lovelettercardgame.utils.toasty
import kotlinx.android.synthetic.main.dialog_add_room.view.*

class AddRoomDialog(private val activity: Activity?) :
    AddRoomContractor.View {

    private lateinit var mPresenter: AddRoomContractor.Presenter
    private val dialog :Dialog = Dialog(activity!!)
    private val view: View
    private val roomNameEditText:AppCompatEditText
    private val maxPlayersRadioGroup:RadioGroup
    private val addRoomButton:Button



    init {
        view=LayoutInflater.from(activity).inflate(R.layout.dialog_add_room,null)
        AddRoomPresenter(this)
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
            val roomName=roomNameEditText.text.toString()
            val maxPlayersNumber=selectedRadioButton.text.toString()
            mPresenter.addRoom(roomName,maxPlayersNumber)
        }
    }


    fun show(){
        dialog.window?.attributes?.windowAnimations=R.style.CardInfoDialogAnimation
        dialog.show()
    }

    override fun showMessage(message: String?, messageType: Constants.MessageType) {
        if (message!=null)
            getContextOfActivity()?.toasty(message,messageType)
    }

    override fun setPresenter(presenter: AddRoomPresenter) {
        mPresenter=presenter
    }

    override fun newRoomIsAdded(roomPOJO: RoomPOJO) {
        dialog.cancel()
        val gameFragment=GameFragment()
        gameFragment.arguments= bundleOf(RoomsFragment.ARGUMEN_ROOM_ITEM to roomPOJO)
        val  activity=getActivityOfActivity() as MainActivity
        activity.changeFragment(gameFragment)


    }

    override fun getActivityOfActivity(): Activity?=activity
    override fun getContextOfActivity(): Context? =activity
}