package com.johndeweydev.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johndeweydev.notesapp.repository.DefaultNotesRepository

class NotesViewModelFactory(
    private val defaultNotesRepository: DefaultNotesRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DefaultNotesViewModel(defaultNotesRepository) as T
    }
}