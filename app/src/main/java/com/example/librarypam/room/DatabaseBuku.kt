package com.example.librarypam.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Buku::class, Kategori::class, Riwayat::class], version = 1, exportSchema = false)
abstract class DatabaseBuku : RoomDatabase() {
    abstract fun bukuDao(): BukuDao

    companion object {
        @Volatile
        private var Instance: DatabaseBuku? = null

        fun getDatabase(context: Context): DatabaseBuku {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DatabaseBuku::class.java, "db_buku")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}