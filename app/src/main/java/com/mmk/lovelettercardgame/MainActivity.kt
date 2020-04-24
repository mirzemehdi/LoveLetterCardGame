package com.mmk.lovelettercardgame

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mmk.lovelettercardgame.ui.playrooms.RoomsFragment
import com.wajahatkarim3.easyflipview.EasyFlipView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainFrameLayout:FrameLayout by lazy { frame_layout_main }
    private val fragmentManager:FragmentManager=supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeFragment(RoomsFragment())

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

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount>1)
            fragmentManager.popBackStack()
        else
            finish()
    }


}
