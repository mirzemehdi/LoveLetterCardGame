package com.mmk.lovelettercardgame.ui.activities.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.activities.login.LoginActivity
import com.mmk.lovelettercardgame.ui.activities.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity(), SplashContractor.View {
    private lateinit var mPresenter: SplashContractor.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_splash)

        image_view_splash_logo.animate().apply {
            duration=1500L
            alpha(1f)
            interpolator=AccelerateInterpolator()
        }

        SplashPresenter(this)
        mPresenter.onCheckAccount()
    }

    override fun startLoginPage() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()

    }

    override fun startMainPage() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun getActivityOfActivity(): Activity?=this

    override fun getContextOfActivity(): Context?=this

    override fun setPresenter(presenter: SplashPresenter) {
        mPresenter=presenter
    }
}
