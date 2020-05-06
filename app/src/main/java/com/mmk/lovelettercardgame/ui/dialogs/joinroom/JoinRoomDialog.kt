package com.mmk.lovelettercardgame.ui.dialogs.joinroom

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatEditText
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.ui.activities.MainActivity
import com.mmk.lovelettercardgame.ui.fragments.game.GameFragment
import com.mmk.lovelettercardgame.utils.Constants
import com.mmk.lovelettercardgame.utils.toasty
import kotlinx.android.synthetic.main.dialog_join_player.view.*

class JoinRoomDialog(
    private val activity: Activity?,
    val roomId: String,
    var joined: (player: PlayerPOJO) -> Unit
) : JoinRoomContractor.View {

    private lateinit var mPresenter: JoinRoomContractor.Presenter
    private val dialog: Dialog = Dialog(activity!!)
    private val view: View
    private val playerNameEditText: AppCompatEditText
    private val joinRoomButton: Button


    init {
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_join_player, null)
        JoinRoomPresenter(this)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        playerNameEditText = view.edit_text_join_room_player_name
        joinRoomButton = view.button_dialog_join_room_join
        setClicks()



    }

    private fun setClicks() {
        joinRoomButton.setOnClickListener {
            val playerName = playerNameEditText.text.toString()
            mPresenter.joinRoom(playerName,roomId)

        }
        dialog.setOnKeyListener(object :DialogInterface.OnKeyListener{
            override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode==KeyEvent.KEYCODE_BACK){
                    val mainactivity =activity
                    mainactivity as MainActivity
                    dialog?.dismiss()
                    mainactivity.finishFragment()
                    return true
                }
                return false

            }
        })

    }


    fun show() {
        dialog.window?.attributes?.windowAnimations = R.style.CardInfoDialogAnimation
        dialog.show()
    }

    override fun showMessage(message: String?, messageType: Constants.MessageType) {
        if (message != null)
            getContextOfActivity()?.toasty(message, messageType)
    }

    override fun setPresenter(presenter: JoinRoomPresenter) {
        mPresenter = presenter
    }

    override fun playerIsJoined(playerPOJO: PlayerPOJO) {
        dialog.cancel()
        joined(playerPOJO)

    }


    override fun getActivityOfActivity(): Activity? = activity
    override fun getContextOfActivity(): Context? = activity

}