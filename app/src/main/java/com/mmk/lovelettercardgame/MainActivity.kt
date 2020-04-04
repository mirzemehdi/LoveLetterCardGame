package com.mmk.lovelettercardgame

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.wajahatkarim3.easyflipview.EasyFlipView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({ viewFlipper.flipTheView(true) },1000)
        viewFlipper.onFlipListener =
            EasyFlipView.OnFlipAnimationListener { _, _ ->
                if (viewFlipper.isFrontSide) viewFlipper.setFlipTypeFromLeft()
                else viewFlipper.setFlipTypeFromRight()
            }



    }




}
