package com.johndeweydev.notesapp.models.requestModels

data class NoteUpdateRequest(
    val id: Int,
    val title: String,
    val description: String?
)
