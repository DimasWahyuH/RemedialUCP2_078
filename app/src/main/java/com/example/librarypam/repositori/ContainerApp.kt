package com.example.librarypam.repositori

import android.content.Context
import com.example.librarypam.room.DatabaseBuku

interface AppContainer {
    val repositoriBuku: RepositoriBuku
}

class AppDataContainer(private val context: Context) : AppContainer {

    private val database: DatabaseBuku by lazy {
        DatabaseBuku.getDatabase(context)
    }

    override val repositoriBuku: RepositoriBuku by lazy {
        OfflineRepositoriBuku(database.bukuDao(), database)
    }
}