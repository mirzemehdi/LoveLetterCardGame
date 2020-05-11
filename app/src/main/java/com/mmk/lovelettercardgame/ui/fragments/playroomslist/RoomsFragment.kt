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
import com.mmk.lovelettercardgame.ui.dialogs.addroom.AddRoomDialog
import com.mmk.lovelettercardgame.ui.fragments.game.GameFragment
import com.mmk.lovelettercardgame.utils.*
import kotlinx.android.synthetic.main.fragment_rooms.*
import kotlinx.android.synthetic.main.fragment_rooms.view.*
import kotlinx.android.synthetic.main.fragment_rooms.view.progressBar_rooms_loading

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.getRoomList()
    }

    override fun onDestroyView() {
        super.onDestroyView()

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
            val dialog=
                AddRoomDialog(
                    getActivityOfActivity()
                )
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
        if(view==null) return
        if (isLoading)progressBar_rooms_loading.visibility=View.VISIBLE
        else progressBar_rooms_loading.visibility=View.GONE
    }

    override fun showRoomList(roomsList: List<RoomPOJO>) {
       roomsAdapter.setRoomList(roomsList)
    }

    override fun showMessage(message: String,type:Constants.MessageType) {
        getContextOfActivity()?.toasty(message,type)
    }

    override fun getActivityOfActivity(): Activity? = activity

    override fun getContextOfActivity(): Context? = context

    override fun setPresenter(presenter: RoomsPresenter) {
        mPresenter = presenter
    }


}
