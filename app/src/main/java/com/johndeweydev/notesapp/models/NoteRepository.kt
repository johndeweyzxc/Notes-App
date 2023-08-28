package com.johndeweydev.notesapp.models

data class NoteRepository<model> (
    val dataModel: model?,
    val statusCode: Int,
)