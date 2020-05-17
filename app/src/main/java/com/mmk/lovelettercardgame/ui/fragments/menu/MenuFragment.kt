package com.mmk.lovelettercardgame.ui.fragments.menu

import android.media.MediaPlayer
import android.os.Bundle
import android.view.SoundEffectConstants
import androidx.fragment.app.Fragment
import android.view.View

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.activities.main.MainActivity
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.helpers.Helper
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private var backgroundMusicPlayer:MediaPlayer?= null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backgroundMusicPlayer=MediaPlayer.create(context,R.raw.bg_game_music)
        backgroundMusicPlayer?.isLooping=true
        backgroundMusicPlayer?.start()
        setClicks()
    }

    override fun onPause() {
        super.onPause()
        backgroundMusicPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        backgroundMusicPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundMusicPlayer?.stop()
    }

    private fun setClicks() {
        val hostActivity=activity as MainActivity
        text_view_menu_play.setOnClickListener {
            Helper.makeClickSound(hostActivity)
            hostActivity.changeFragment(RoomsFragment())
        }
        text_view_menu_exit.setOnClickListener {
            Helper.makeClickSound(hostActivity)
            hostActivity.finish()
        }
        text_view_menu_profile.setOnClickListener {
            Helper.makeClickSound(hostActivity)


        }
        text_view_menu_settings.setOnClickListener {
            Helper.makeClickSound(hostActivity)

        }
    }

}
