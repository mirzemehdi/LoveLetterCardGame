package com.mmk.lovelettercardgame.ui.activities.splash

import com.mmk.lovelettercardgame.ui.BaseView

interface SplashContractor {
    interface View : BaseView<SplashPresenter> {
        fun startLoginPage()
        fun startMainPage()
    }

    interface Presenter {
        fun onCheckAccount()
    }
}