package com.johndeweydev.notesapp.models.responseModels

data class NoteUpdateResponse(
    val id: Int,
    val title: String,
    val updated_at: Int,
    val description: String?
)
