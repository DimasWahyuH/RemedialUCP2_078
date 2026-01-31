package com.example.librarypam.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarypam.repositori.RepositoriBuku
import com.example.librarypam.room.Buku
import com.example.librarypam.room.Kategori
import kotlinx.coroutines.launch

class EntryViewModel(private val repositori: RepositoriBuku) : ViewModel() {

    var uiStateBuku by mutableStateOf(BukuUiState())
        private set

    var uiStateKategori by mutableStateOf(KategoriUiState())
        private set

    fun updateUiStateBuku(bukuBaru: BukuUiState) {
        uiStateBuku = bukuBaru
    }

    fun updateUiStateKategori(kategoriBaru: KategoriUiState) {
        uiStateKategori = kategoriBaru
    }

    fun simpanBuku() {
        viewModelScope.launch {
            repositori.insertBuku(uiStateBuku.toBuku())
        }
    }

    fun simpanKategori() {
        viewModelScope.launch {
            repositori.insertKategori(uiStateKategori.toKategori())
        }
    }
}

data class BukuUiState(
    val judul: String = "",
    val penulis: String = "",
    val status: String = "Tersedia",
    val idKategori: Int? = null
)

fun BukuUiState.toBuku(): Buku = Buku(
    judul = judul,
    penulis = penulis,
    statusBuku = status,
    idKategori = idKategori
)

data class KategoriUiState(
    val nama: String = "",
    val deskripsi: String = ""
)

fun KategoriUiState.toKategori(): Kategori = Kategori(
    nama = nama,
    deskripsi = deskripsi
)