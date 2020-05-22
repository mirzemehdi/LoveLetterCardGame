package com.mmk.lovelettercardgame.ui.activities.splash


import com.mmk.lovelettercardgame.manager.PreferencesManager
import java.lang.Thread.sleep

class SplashPresenter(private val mView: SplashContractor.View) : SplashContractor.Presenter {
    init {
        mView.setPresenter(this)
    }

    override fun onCheckAccount() {
       val thread=Thread {
           run {
               try {
                   sleep(2000)
                   val isUserLogged= PreferencesManager
                           .getInstance(mView.getContextOfActivity())
                           .isUserLoggedIn()
                   if (isUserLogged) {
                       mView.startMainPage()
                   } else {
                       mView.startLoginPage()
                   }
               } catch (e: InterruptedException) {
                   mView.startLoginPage()
               }
           }

       }
        thread.start()
    }
}