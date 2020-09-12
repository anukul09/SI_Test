package com.example.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.data.roomdb.Team1
import com.example.myapplication.data.roomdb.Team2
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: MyViewModelFactory by instance()

    private lateinit var viewModel: MyViewModel

    var cTeamList1 = ArrayList<Team1>()
    var cTeamList2 = ArrayList<Team2>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    fun initViews(){

        viewModel = ViewModelProviders.of(this, factory).get(MyViewModel::class.java)
        viewModel.getAllProfiles()

        viewModel.mRespTeam1.observe(this, Observer {
            cTeamList1 = it as ArrayList<Team1>
        })

        viewModel.mRespTeam2.observe(this, Observer {
            cTeamList2 = it as ArrayList<Team2>
        })

    buttonTeam1.setOnClickListener {
        val bottomSheetFragment = DisplayTeamBS(cTeamList1,null)
        bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomsheetDialog)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

    }

    buttonTeam2.setOnClickListener {
            val bottomSheetFragment = DisplayTeamBS(null,cTeamList2)
            bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomsheetDialog)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

    }
    }
}

