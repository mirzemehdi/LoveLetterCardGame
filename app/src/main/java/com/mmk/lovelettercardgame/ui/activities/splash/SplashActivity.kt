package com.mmk.lovelettercardgame.ui.activities.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.ui.activities.login.LoginActivity
import com.mmk.lovelettercardgame.ui.activities.main.MainActivity


class SplashActivity : AppCompatActivity(), SplashContractor.View {
    private lateinit var mPresenter: SplashContractor.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
