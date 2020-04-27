package com.mmk.lovelettercardgame.ui.fragments.playroomslist


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.mmk.lovelettercardgame.R
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.activities.MainActivity
import com.mmk.lovelettercardgame.ui.dialogs.AddRoomDialog
import com.mmk.lovelettercardgame.ui.fragments.game.GameFragment
import com.mmk.lovelettercardgame.utils.InfiniteScrollListener
import com.mmk.lovelettercardgame.utils.inflate
import com.mmk.lovelettercardgame.utils.toast
import kotlinx.android.synthetic.main.fragment_rooms.view.*

/**
 * A simple [Fragment] subclass.
 */
class RoomsFragment : Fragment(),
    RoomsContractor.View {

    companion object{
        val ARGUMEN_ROOM_ITEM="ARGUMENT_ROOM_ITEM"
    }

    private lateinit var mPresenter: RoomsContractor.Presenter
    private lateinit var roomsRecyclerView:RecyclerView
    private lateinit var roomsAdapter: RoomsAdapter
    private lateinit var infiniteScrollListener: InfiniteScrollListener
    private lateinit var roomAddButton:FloatingActionButton

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
        val layoutManager= LinearLayoutManager(getActivityOfActivity())
        roomsRecyclerView.layoutManager = layoutManager
        roomsRecyclerView.setHasFixedSize(true)
        roomsRecyclerView.adapter=roomsAdapter

        infiniteScrollListener=InfiniteScrollListener(layoutManager){
            //TODO ADD NEW ROOMS

        }
        roomsRecyclerView.addOnScrollListener(infiniteScrollListener)

        roomAddButton=view.button_fragment_rooms_add_room
        setClicks()


    }

    private fun setClicks() {
        roomAddButton.setOnClickListener {
            val dialog=AddRoomDialog(getContextOfActivity())
            dialog.show()
        }

        roomsAdapter.roomItemClicked={roomItem ->

            // Go to Game Fragment
            val gameFragment=GameFragment()
            gameFragment.arguments= bundleOf(ARGUMEN_ROOM_ITEM to roomItem)
            val  activity=getActivityOfActivity() as MainActivity
            activity.changeFragment(gameFragment)
        }
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
