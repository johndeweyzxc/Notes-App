package com.johndeweydev.notesapp.viewmodels

import com.johndeweydev.notesapp.models.Note
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
interface NotesViewModel {
    fun getAllNote()
    fun updateNote(currentNoteData: Note, noteUpdateRequest: NoteUpdateRequest)
    fun uploadNote(noteUploadRequest: NoteUploadRequest)
    fun deleteNote(id: Int)
}