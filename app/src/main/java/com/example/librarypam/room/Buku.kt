package com.example.librarypam.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "kategori")
data class Kategori(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val deskripsi: String,
    val indukId: Int? = null,
    val aktif: Boolean = true
)

@Entity(
    tableName = "buku",
    foreignKeys = [
        ForeignKey(
            entity = Kategori::class,
            parentColumns = ["id"],
            childColumns = ["idKategori"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Buku(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val penulis: String,
    val statusBuku: String,
    val idKategori: Int?,
    val aktif: Boolean = true
)

@Entity(tableName = "riwayat")
data class Riwayat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tanggal: Long = System.currentTimeMillis(),
    val aksi: String,
    val info: String
)