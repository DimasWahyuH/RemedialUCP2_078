package com.example.librarypam.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarypam.room.Buku
import com.example.librarypam.room.Kategori
import com.example.librarypam.view.route.DestinasiNavigasi
import com.example.librarypam.viewmodel.HomeUiState
import com.example.librarypam.viewmodel.HomeViewModel
import com.example.librarypam.viewmodel.provider.PenyediaViewModel

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inventaris Buku") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah")
            }
        },
    ) { innerPadding ->
        BodyHome(
            homeUiState = uiState,
            modifier = Modifier.padding(innerPadding),
            onDeleteKategori = { id, hapusBuku ->
                viewModel.deleteKategori(id, hapusBuku)
            }
        )
    }
}

@Composable
fun BodyHome(
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
    onDeleteKategori: (Int, Boolean) -> Unit
) {
    if (homeUiState.listKategori.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Tidak ada data kategori")
        }
    } else {
        ListKategori(
            listKategori = homeUiState.listKategori,
            listBuku = homeUiState.listBuku,
            modifier = modifier,
            onDelete = onDeleteKategori
        )
    }
}

@Composable
fun ListKategori(
    listKategori: List<Kategori>,
    listBuku: List<Buku>,
    modifier: Modifier = Modifier,
    onDelete: (Int, Boolean) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = listKategori, key = { it.id }) { kategori ->
            val bukuDiKategori = listBuku.filter { it.idKategori == kategori.id }
            ItemKategori(
                kategori = kategori,
                jumlahBuku = bukuDiKategori.size,
                onDelete = onDelete
            )
        }
    }
}

@Composable
fun ItemKategori(
    kategori: Kategori,
    jumlahBuku: Int,
    onDelete: (Int, Boolean) -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    var hapusBukuJuga by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = kategori.nama,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = kategori.deskripsi,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Jumlah Buku: $jumlahBuku",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                IconButton(onClick = { deleteConfirmationRequired = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
        }
    }

    if (deleteConfirmationRequired) {
        AlertDialog(
            onDismissRequest = { deleteConfirmationRequired = false },
            title = { Text("Hapus Kategori") },
            text = {
                Column {
                    Text("Apakah Anda yakin ingin menghapus kategori ini?")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = hapusBukuJuga,
                            onCheckedChange = { hapusBukuJuga = it }
                        )
                        Text("Hapus buku di dalamnya juga?")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    deleteConfirmationRequired = false
                    onDelete(kategori.id, hapusBukuJuga)
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteConfirmationRequired = false }) {
                    Text("Batal")
                }
            }
        )
    }
}