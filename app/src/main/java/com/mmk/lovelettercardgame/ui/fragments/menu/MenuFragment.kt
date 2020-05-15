package com.mmk.lovelettercardgame.ui.fragments.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.activities.main.MainActivity
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment(R.layout.fragment_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClicks()
    }

    private fun setClicks() {
        val hostActivity=activity as MainActivity

        text_view_menu_play.setOnClickListener {
            hostActivity.changeFragment(RoomsFragment())
        }
        text_view_menu_exit.setOnClickListener {
            hostActivity.finish()
        }
    }

}
