package com.mmk.lovelettercardgame.ui.fragments.game


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.fragments.playroomslist.RoomsFragment
import com.mmk.lovelettercardgame.utils.inflate
import com.mmk.lovelettercardgame.utils.toast
import kotlinx.android.synthetic.main.fragment_game.*

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
     lateinit var roomItem:RoomPOJO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=container?.inflate(R.layout.fragment_game)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomItem=arguments?.getSerializable(RoomsFragment.ARGUMEN_ROOM_ITEM) as RoomPOJO

    }


}
