package com.example.myapplication.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.adapters.ProfilesAdapter
import com.example.myapplication.data.roomdb.Team1
import com.example.myapplication.data.roomdb.Team2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_main.*

class DisplayTeamBS(
    var cTeamList1: ArrayList<Team1>?,
    var cTeamList2: ArrayList<Team2>?
) : BottomSheetDialogFragment() {

    private var m_cBehavior             : BottomSheetBehavior<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val lcDialog            = super.onCreateDialog(savedInstanceState)
        val lcView              = View.inflate(context, R.layout.fragment_main, null)
        lcDialog.setContentView(lcView)

        m_cBehavior                     = BottomSheetBehavior.from(lcView.parent as View)
        m_cBehavior?.state = BottomSheetBehavior.STATE_EXPANDED

        m_cBehavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_DRAGGING)
                {
                    m_cBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

        })

        return lcDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

            recycler_view_TeamList.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                var adapter = ProfilesAdapter(cTeamList1,cTeamList2)
                recycler_view_TeamList.adapter = adapter
            }
        //isCancelable = false
    }
}