package com.mmk.lovelettercardgame.manager

import android.content.Context
import android.content.SharedPreferences

import com.mmk.lovelettercardgame.utils.Constants

object PreferencesManager {

    private var sharedPreferences: SharedPreferences? = null

    fun getInstance(context: Context?): PreferencesManager {
        sharedPreferences = context?.getSharedPreferences("LoveLetterPreferences", Context.MODE_PRIVATE)
        return this
    }



    fun saveToken(token:String) {

        sharedPreferences?.edit()?.putString(Constants.PREF_USER_TOKEN,token)?.apply()
        sharedPreferences?.edit()?.putBoolean(Constants.PREF_USER_EXIST, true)?.apply()
    }

    fun getSavedToken():String?{
        return sharedPreferences?.getString(Constants.PREF_USER_TOKEN,null) ?: return null
    }

    fun isUserLoggedIn():Boolean {
        val isUserLogged= sharedPreferences?.getBoolean(Constants.PREF_USER_EXIST,true)?:false
        return isUserLogged
    }

    fun resetAll() {
        sharedPreferences?.edit()?.clear()?.apply()
    }
}