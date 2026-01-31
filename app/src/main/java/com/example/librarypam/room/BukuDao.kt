package com.example.librarypam.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BukuDao {
    @Query("SELECT * FROM kategori WHERE aktif = 1 ORDER BY nama ASC")
    fun getKategori(): Flow<List<Kategori>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun tambahKategori(kategori: Kategori)

    @Update
    suspend fun ubahKategori(kategori: Kategori)

    @Query("UPDATE kategori SET aktif = 0 WHERE id = :id")
    suspend fun hapusKategori(id: Int)

    @Query("SELECT * FROM buku WHERE aktif = 1 ORDER BY judul ASC")
    fun getBuku(): Flow<List<Buku>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun tambahBuku(buku: Buku)

    @Query("SELECT COUNT(*) FROM buku WHERE idKategori = :idKategori AND statusBuku = 'Dipinjam' AND aktif = 1")
    suspend fun cekAdaPinjaman(idKategori: Int): Int

    @Query("UPDATE buku SET idKategori = NULL WHERE idKategori = :idKategori AND aktif = 1")
    suspend fun lepasKategori(idKategori: Int)

    @Query("UPDATE buku SET aktif = 0 WHERE idKategori = :idKategori")
    suspend fun hapusBukuDiKategori(idKategori: Int)

    @Insert
    suspend fun tambahRiwayat(riwayat: Riwayat)
}