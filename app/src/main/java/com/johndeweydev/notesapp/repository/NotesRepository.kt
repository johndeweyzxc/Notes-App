package com.johndeweydev.notesapp.repository

import com.johndeweydev.notesapp.models.NoteDataHolder
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.models.responseModels.NoteDeleteResponse
import com.johndeweydev.notesapp.models.responseModels.NoteGetAllResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUpdateResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUploadResponse
interface NotesRepository {
    suspend fun getAllNotes(): NoteDataHolder<NoteGetAllResponse?>
    suspend fun updateNote(noteUpdateRequest: NoteUpdateRequest): NoteDataHolder<NoteUpdateResponse>
    suspend fun uploadNote(noteUploadRequest: NoteUploadRequest): NoteDataHolder<NoteUploadResponse>
    suspend fun deleteNote(id: Int): NoteDataHolder<NoteDeleteResponse>
}