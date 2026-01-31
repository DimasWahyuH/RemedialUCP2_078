package com.example.librarypam

import android.app.Application
import com.example.librarypam.repositori.AppDataContainer
import com.example.librarypam.repositori.AppContainer

class LibraryApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}