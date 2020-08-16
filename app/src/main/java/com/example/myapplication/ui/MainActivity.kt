package com.example.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.adapters.ProfilesAdapter
import com.example.myapplication.data.roomdb.ProfileEntity
import com.example.myapplication.util.hide
import com.example.myapplication.util.show
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: MyViewModelFactory by instance()

    private lateinit var viewModel: MyViewModel

    var cProfilesList = ArrayList<ProfileEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    fun initViews(){

        pbProfiles.show()
        viewModel = ViewModelProviders.of(this, factory).get(MyViewModel::class.java)
        viewModel.getAllProfiles()

        viewModel.mProfilesReponse.observe(this, Observer {

            pbProfiles.hide()
            rvDisplayProfiles.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                cProfilesList = it as ArrayList<ProfileEntity>
                var adapter = ProfilesAdapter(cProfilesList)
                rvDisplayProfiles.adapter = adapter
            }

        })
    }
}

