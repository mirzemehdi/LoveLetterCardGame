package com.mmk.lovelettercardgame.ui.dialogs.allcards

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.mmk.lovelettercardgame.R
import kotlinx.android.synthetic.main.dialog_all_cards.view.*

class AllCardsDialog(
    private val activity: Activity?,
    private var cardSelected: (cardType: String) -> Unit
) :View.OnClickListener,View.OnTouchListener{


    private val dialog: Dialog = Dialog(activity!!)
    private val view: View



    init {
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_all_cards, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        setClicks()



    }

    private fun setClicks() {

        view.image_view_all_cards_baron.setOnClickListener(this)
        view.image_view_all_cards_countess.setOnClickListener(this)

        view.image_view_all_cards_handmaid.setOnClickListener(this)

        view.image_view_all_cards_king.setOnClickListener(this)

        view.image_view_all_cards_priest.setOnClickListener(this)

        view.image_view_all_cards_prince.setOnClickListener(this)

        view.image_view_all_cards_princess.setOnClickListener(this)


        view.image_view_all_cards_baron.setOnTouchListener(this)
        view.image_view_all_cards_countess.setOnTouchListener(this)

        view.image_view_all_cards_handmaid.setOnTouchListener(this)

        view.image_view_all_cards_king.setOnTouchListener(this)

        view.image_view_all_cards_priest.setOnTouchListener(this)

        view.image_view_all_cards_prince.setOnTouchListener(this)

        view.image_view_all_cards_princess.setOnTouchListener(this)




    }

    override fun onClick(v: View?) {
        val cardType=v?.tag as String
        println("Card selected guard: $cardType")
        cardSelected(cardType)
        dialog.dismiss()

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action){
            MotionEvent.ACTION_DOWN ->
                v?.setPadding(15,15,15,15)
            MotionEvent.ACTION_UP ->
                v?.setPadding(0,0,0,0)
        }
        v?.performClick()
        return true
    }

    fun show() {
        dialog.window?.attributes?.windowAnimations = R.style.CardInfoDialogAnimation
        dialog.show()
    }



}