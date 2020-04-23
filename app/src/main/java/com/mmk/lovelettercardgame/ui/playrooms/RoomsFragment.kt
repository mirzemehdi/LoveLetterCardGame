package com.mmk.lovelettercardgame.ui.playrooms


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.inflate

/**
 * A simple [Fragment] subclass.
 */
class RoomsFragment : Fragment(),RoomsContractor.View {
    private lateinit var mPresenter:RoomsContractor.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RoomsPresenter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=container?.inflate(R.layout.fragment_rooms)
        return view
    }


    override fun showRoomList(roomsList: List<RoomPOJO>) {
        TODO("Not yet implemented")
    }


    override fun getActivityOfActivity(): Activity?=activity

    override fun getContextOfActivity(): Context?=context

    override fun setPresenter(presenter: RoomsPresenter) {
        mPresenter=presenter
    }


}
