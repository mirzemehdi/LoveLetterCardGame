package com.mmk.lovelettercardgame.ui.activities.main

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.intractor.RoomsIntractor
import com.mmk.lovelettercardgame.ui.fragments.game.GameFragment
import com.mmk.lovelettercardgame.ui.fragments.menu.MenuFragment
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainFrameLayout:FrameLayout by lazy { frame_layout_main }
    private val fragmentManager:FragmentManager=supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
        changeFragment(MenuFragment())

//        Handler().postDelayed({ viewFlipper.flipTheView(true) },1000)
//        viewFlipper.onFlipListener =
//            EasyFlipView.OnFlipAnimationListener { _, _ ->
//                if (viewFlipper.isFrontSide) viewFlipper.setFlipTypeFromLeft()
//                else viewFlipper.setFlipTypeFromRight()
//            }
//
//
//        var dialog=Dialog(this)
//        dialog.setContentView(R.layout.user_box_view)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.show()



    }

     fun changeFragment(fragment:Fragment) {
        fragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(mainFrameLayout.id,fragment)
            .commit()
    }

    fun finishFragment(){
        fragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount>1)
            fragmentManager.popBackStack()
        else
            finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        RoomsIntractor().closeServer()
    }


}
