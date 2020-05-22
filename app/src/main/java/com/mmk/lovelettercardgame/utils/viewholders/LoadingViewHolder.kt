package com.mmk.lovelettercardgame.utils.viewholders

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_loading.view.*

class LoadingViewHolder( itemView:View): RecyclerView.ViewHolder(itemView) {
    private val mProgress:ProgressBar = itemView.item_progress
    fun onBind(){mProgress.isIndeterminate=true}
}