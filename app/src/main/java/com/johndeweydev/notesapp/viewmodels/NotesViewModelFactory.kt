package com.johndeweydev.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johndeweydev.notesapp.repository.NotesRepository

class NotesViewModelFactory(
    private val notesRepository: NotesRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepository) as T
    }
}