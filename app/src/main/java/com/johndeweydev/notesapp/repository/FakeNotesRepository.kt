package com.johndeweydev.notesapp.repository

import com.johndeweydev.notesapp.models.NoteDataHolder
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.models.responseModels.NoteDeleteResponse
import com.johndeweydev.notesapp.models.responseModels.NoteGetAllResponse
import com.johndeweydev.notesapp.models.responseModels.NoteGetResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUpdateResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUploadResponse

class FakeNotesRepository : NotesRepository {

    var listOfNoteGetResponse = mutableListOf<NoteGetResponse>()

    override suspend fun getAllNotes(): NoteDataHolder<NoteGetAllResponse?> {
        val noteGetAllResponse = NoteGetAllResponse(listOfNoteGetResponse)
        return NoteDataHolder(noteGetAllResponse, 200)
    }

    override suspend fun updateNote(
        noteUpdateRequest: NoteUpdateRequest
    ): NoteDataHolder<NoteUpdateResponse> {
        val noteUpdateResponse = NoteUpdateResponse(
            noteUpdateRequest.id,
            noteUpdateRequest.title,
            44,
            noteUpdateRequest.description
        )

        return NoteDataHolder(noteUpdateResponse, 201)
    }

    override suspend fun uploadNote(
        noteUploadRequest: NoteUploadRequest
    ): NoteDataHolder<NoteUploadResponse> {
        val noteUploadResponse = NoteUploadResponse(
            4,
            noteUploadRequest.title,
            4,
            noteUploadRequest.description
        )

        return NoteDataHolder(noteUploadResponse, 201)
    }

    override suspend fun deleteNote(id: Int): NoteDataHolder<NoteDeleteResponse> {
        val noteDeleteResponse = NoteDeleteResponse(44)
        return NoteDataHolder(noteDeleteResponse, 200)
    }

}