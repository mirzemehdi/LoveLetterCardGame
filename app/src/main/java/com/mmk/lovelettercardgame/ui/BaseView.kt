package com.mmk.lovelettercardgame.ui

import android.app.Activity
import android.content.Context

 interface BaseView<T> {
    fun getActivityOfActivity():Activity?
    fun getContextOfActivity():Context?
    fun setPresenter(presenter:T)
}