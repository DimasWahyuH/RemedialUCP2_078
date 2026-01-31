package com.example.librarypam.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.librarypam.LibraryApp
import com.example.librarypam.viewmodel.EntryViewModel
import com.example.librarypam.viewmodel.HomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(libraryApp().container.repositoriBuku)
        }

        initializer {
            EntryViewModel(libraryApp().container.repositoriBuku)
        }
    }
}

fun CreationExtras.libraryApp(): LibraryApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibraryApp)