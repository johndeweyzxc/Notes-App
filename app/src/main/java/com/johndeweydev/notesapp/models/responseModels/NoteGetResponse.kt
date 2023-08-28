package com.johndeweydev.notesapp.models.responseModels

import com.johndeweydev.notesapp.models.NoteDateInfo

data class NoteGetResponse(
    val id: Int,
    val title: String,
    val description: String?,
    val date_information: NoteDateInfo
)
