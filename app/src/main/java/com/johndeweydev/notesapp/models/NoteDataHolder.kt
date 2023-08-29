package com.johndeweydev.notesapp.models

data class NoteDataHolder<model> (
    val dataModel: model?,
    val statusCode: Int,
)