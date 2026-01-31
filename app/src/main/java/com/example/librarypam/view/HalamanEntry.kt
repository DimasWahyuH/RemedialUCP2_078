package com.example.librarypam.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarypam.view.route.DestinasiNavigasi
import com.example.librarypam.viewmodel.DetailBukuUiState
import com.example.librarypam.viewmodel.DetailKategoriUiState
import com.example.librarypam.viewmodel.EntryViewModel
import com.example.librarypam.viewmodel.provider.PenyediaViewModel

object DestinasiEntry : DestinasiNavigasi {
    override val route = "entry"
    override val titleRes = "Entry Data"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntry(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Kategori", "Buku")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Tambah Data") })
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (tabIndex == 0) {
                FormKategori(
                    kategoriUiState = viewModel.uiStateKategori,
                    onValueChange = viewModel::updateUiStateKategori,
                    onSave = {
                        viewModel.saveKategori()
                        navigateBack()
                    }
                )
            } else {
                FormBuku(
                    bukuUiState = viewModel.uiStateBuku,
                    onValueChange = viewModel::updateUiStateBuku,
                    onSave = {
                        viewModel.saveBuku()
                        navigateBack()
                    }
                )
            }
        }
    }
}

@Composable
fun FormKategori(
    kategoriUiState: DetailKategoriUiState,
    onValueChange: (DetailKategoriUiState) -> Unit,
    onSave: () -> Unit
) {
    Column {
        OutlinedTextField(
            value = kategoriUiState.nama,
            onValueChange = { onValueChange(kategoriUiState.copy(nama = it)) },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = kategoriUiState.deskripsi,
            onValueChange = { onValueChange(kategoriUiState.copy(deskripsi = it)) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Kategori")
        }
    }
}

@Composable
fun FormBuku(
    bukuUiState: DetailBukuUiState,
    onValueChange: (DetailBukuUiState) -> Unit,
    onSave: () -> Unit
) {
    Column {
        OutlinedTextField(
            value = bukuUiState.judul,
            onValueChange = { onValueChange(bukuUiState.copy(judul = it)) },
            label = { Text("Judul Buku") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = bukuUiState.penulis,
            onValueChange = { onValueChange(bukuUiState.copy(penulis = it)) },
            label = { Text("Penulis") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = bukuUiState.status,
            onValueChange = { onValueChange(bukuUiState.copy(status = it)) },
            label = { Text("Status (Tersedia/Dipinjam)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = bukuUiState.idKategori?.toString() ?: "",
            onValueChange = {
                onValueChange(bukuUiState.copy(idKategori = it.toIntOrNull()))
            },
            label = { Text("ID Kategori (Angka)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Buku")
        }
    }
}