package com.mmk.lovelettercardgame.ui.fragments.playrooms


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.utils.InfiniteScrollListener
import com.mmk.lovelettercardgame.utils.callbacks.ILoadMore
import com.mmk.lovelettercardgame.utils.inflate
import kotlinx.android.synthetic.main.fragment_rooms.*
import kotlinx.android.synthetic.main.fragment_rooms.view.*

/**
 * A simple [Fragment] subclass.
 */
class RoomsFragment : Fragment(),
    RoomsContractor.View {
    private lateinit var mPresenter: RoomsContractor.Presenter
    private lateinit var roomsRecyclerView:RecyclerView
    private lateinit var roomsAdapter: RoomsAdapter
    private lateinit var infiniteScrollListener: InfiniteScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RoomsPresenter(this)
        roomsAdapter=
            RoomsAdapter()
        mPresenter.getRoomList()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = container?.inflate(R.layout.fragment_rooms)
        initView(view)
        return view
    }

    private fun initView(view: View?) {
        if (view==null) return


        roomsRecyclerView=view.recycler_view_rooms
        val layoutManager:LinearLayoutManager= LinearLayoutManager(getActivityOfActivity())
        roomsRecyclerView.layoutManager = layoutManager
        roomsRecyclerView.setHasFixedSize(true)
        roomsRecyclerView.adapter=roomsAdapter
        infiniteScrollListener=InfiniteScrollListener(layoutManager, object : ILoadMore {
            override fun onLoad() {

            }
        })
        roomsRecyclerView.addOnScrollListener(infiniteScrollListener)

    }


    override fun showItemLoading(isLoading: Boolean) {
        if (isLoading)roomsAdapter.setLoading(true)
        else roomsAdapter.setLoading(false)
    }

    override fun showRoomList(roomsList: List<RoomPOJO>) {
       roomsAdapter.addRoomList(roomsList)
    }


    override fun getActivityOfActivity(): Activity? = activity

    override fun getContextOfActivity(): Context? = context

    override fun setPresenter(presenter: RoomsPresenter) {
        mPresenter = presenter
    }


}
