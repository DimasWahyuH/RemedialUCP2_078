package com.example.librarypam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarypam.repositori.RepositoriBuku
import com.example.librarypam.room.Buku
import com.example.librarypam.room.Kategori
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val listKategori: List<Kategori> = listOf(),
    val listBuku: List<Buku> = listOf(),
    val errorPesan: String? = null,
    val sedangMemuat: Boolean = false
)

class HomeViewModel(private val repositori: RepositoriBuku) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        repositori.getAllKategori(),
        repositori.getAllBuku()
    ) { kategori, buku ->
        HomeUiState(
            listKategori = kategori,
            listBuku = buku
        )
    }
        .catch {
            emit(HomeUiState(errorPesan = it.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(sedangMemuat = true)
        )

    fun hapusKategori(id: Int, hapusBuku: Boolean) {
        viewModelScope.launch {
            try {
                repositori.deleteKategoriAman(id, hapusBuku)
            } catch (e: Exception) {
            }
        }
    }
}