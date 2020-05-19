package com.mmk.lovelettercardgame.ui.fragments.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.activities.main.MainActivity
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.ui.fragments.settings.SettingsFragment
import com.mmk.lovelettercardgame.utils.helpers.Helper
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment(val isCalledFromGame:Boolean=false) : Fragment(R.layout.fragment_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isCalledFromGame){
            text_view_menu_resume.visibility=View.VISIBLE
            text_view_menu_play.visibility=View.GONE
        }
        else{
            text_view_menu_resume.visibility=View.GONE
            text_view_menu_play.visibility=View.VISIBLE
        }
        setClicks()
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
        text_view_menu_settings.setOnClickListener {
            Helper.makeClickSound(hostActivity)
            hostActivity.changeFragment(SettingsFragment(),!isCalledFromGame)

        }
        text_view_menu_how_to_play.setOnClickListener {
            Helper.makeClickSound(hostActivity)

        }

        text_view_menu_resume.setOnClickListener {
            Helper.makeClickSound(hostActivity)
            (activity as MainActivity).finishFragment()
        }
    }

}
