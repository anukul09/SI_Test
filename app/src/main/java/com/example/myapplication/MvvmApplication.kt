package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.data.network.NetworkCAll
import com.example.myapplication.data.repositories.ProfilesRepo
import com.example.myapplication.data.roomdb.AppDatabase
//import com.example.myapplication.data.roomdb.AppDatabase
import com.example.myapplication.ui.MyViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MvvmApplication : Application() , KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MvvmApplication))

        bind() from singleton { NetworkCAll() }
        bind() from singleton { ProfilesRepo(instance())}
        bind() from singleton { AppDatabase(instance()) }
        bind() from provider { MyViewModelFactory(instance()) }

    }

    companion object
    {
        private lateinit var    instance        : MvvmApplication
        internal lateinit var   kodeinInstance  : Kodein
        public lateinit var       context         : Context

        @Synchronized
        fun getInstance(): MvvmApplication = instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        kodeinInstance = kodein
        context = this

    }
    }