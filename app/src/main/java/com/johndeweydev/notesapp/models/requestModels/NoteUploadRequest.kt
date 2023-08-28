package com.johndeweydev.notesapp.models.requestModels

data class NoteUploadRequest(
    val title: String,
    val description: String?
)
