package com.mmk.lovelettercardgame.utils.helpers

import android.app.AlertDialog
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import com.mmk.lovelettercardgame.R

object Helper {

    fun showDialog(context: Context?,title:String?,message:String?){
        val builder=AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNeutralButton(context?.getString(R.string.ok)) { dialog, _ -> dialog.dismiss()  }
        builder.show()
    }



    fun makeClickSound(context: Context?){
        val mediaPlayer=MediaPlayer.create(context,R.raw.click_sound)
        mediaPlayer.start()
    }


}