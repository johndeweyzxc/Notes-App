package com.johndeweydev.notesapp.models.responseModels

data class NoteGetAllResponse(
    val note_list: MutableList<NoteGetResponse>
)
