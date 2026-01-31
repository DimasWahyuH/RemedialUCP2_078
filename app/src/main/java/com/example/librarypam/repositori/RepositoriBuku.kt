package com.example.librarypam.repositori

import androidx.room.withTransaction
import com.example.librarypam.room.Riwayat
import com.example.librarypam.room.Buku
import com.example.librarypam.room.Kategori
import com.example.librarypam.room.BukuDao
import com.example.librarypam.room.DatabaseBuku
import kotlinx.coroutines.flow.Flow

interface RepositoriBuku {
    fun getAllKategori(): Flow<List<Kategori>>
    fun getAllBuku(): Flow<List<Buku>>
    suspend fun insertKategori(kategori: Kategori)
    suspend fun insertBuku(buku: Buku)
    suspend fun deleteKategoriAman(idKategori: Int, hapusIsi: Boolean)
}

class OfflineRepositoriBuku(
    private val dao: BukuDao,
    private val db: DatabaseBuku
) : RepositoriBuku {

    override fun getAllKategori(): Flow<List<Kategori>> = dao.getKategori()
    override fun getAllBuku(): Flow<List<Buku>> = dao.getBuku()

    override suspend fun insertKategori(kategori: Kategori) {
        dao.tambahKategori(kategori)
        dao.tambahRiwayat(Riwayat(aksi = "TAMBAH", info = "Kategori: ${kategori.nama}"))
    }

    override suspend fun insertBuku(buku: Buku) {
        dao.tambahBuku(buku)
        dao.tambahRiwayat(Riwayat(aksi = "TAMBAH", info = "Buku: ${buku.judul}"))
    }

    override suspend fun deleteKategoriAman(idKategori: Int, hapusIsi: Boolean) {
        db.withTransaction {
            val adaPinjam = dao.cekAdaPinjaman(idKategori)

            if (adaPinjam > 0) {
                throw Exception("Gagal: Masih ada buku dipinjam di kategori ini")
            }

            if (hapusIsi) {
                dao.hapusBukuDiKategori(idKategori)
            } else {
                dao.lepasKategori(idKategori)
            }

            dao.hapusKategori(idKategori)

            dao.tambahRiwayat(
                Riwayat(aksi = "HAPUS", info = "Kategori ID $idKategori dihapus")
            )
        }
    }
}