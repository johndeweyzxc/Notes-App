package com.johndeweydev.notesapp.models.responseModels

data class NoteUploadResponse(
    val id: Int,
    val title: String,
    val created_at: Int,
    val description: String?
)
