package com.mmk.lovelettercardgame.ui.dialogs.cardinfo

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.activities.MainActivity
import com.mmk.lovelettercardgame.ui.fragments.game.GameFragment
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsContractor
import com.mmk.lovelettercardgame.utils.Constants
import com.mmk.lovelettercardgame.utils.toast
import com.mmk.lovelettercardgame.utils.toasty
import kotlinx.android.synthetic.main.dialog_add_room.view.*
import kotlinx.android.synthetic.main.dialog_card_info.view.*

class CardDetailInfoDialog(private val activity: Activity?,val card: ImageView) {

    private val dialog :Dialog = Dialog(activity!!)
    private val view: View
    private val cardImageView:ImageView




    init {
        view=LayoutInflater.from(activity).inflate(R.layout.dialog_card_info,null)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        cardImageView=view.image_view_dialog_card_info
        cardImageView.setImageDrawable(card.drawable)

    }

    fun show(){
        dialog.window?.attributes?.windowAnimations=R.style.CardInfoDialogAnimation
        dialog.show()
        card.visibility=View.INVISIBLE
        dialog.setOnDismissListener { card.visibility=View.VISIBLE }
    }


}