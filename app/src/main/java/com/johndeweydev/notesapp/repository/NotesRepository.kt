package com.johndeweydev.notesapp.repository

import android.util.Log
import com.johndeweydev.notesapp.data.RetrofitInstance
import com.johndeweydev.notesapp.models.NoteRepository
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.models.responseModels.NoteDeleteResponse
import com.johndeweydev.notesapp.models.responseModels.NoteGetAllResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUpdateResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUploadResponse

class NotesRepository {

    suspend fun getAllNotes(): NoteRepository<NoteGetAllResponse?> {
        try {
            val noteGetAllResponse = RetrofitInstance.notesApi.getAllNotes()
            if (noteGetAllResponse.isSuccessful) {
                val statusCode = noteGetAllResponse.code()
                return NoteRepository(noteGetAllResponse.body(), statusCode)
            } else {
                Log.d("dev-log", "get all notes: Request unsuccessful")
            }
        } catch (e: java.net.ConnectException) {
            Log.d("dev-log", e.message.toString())
        }
        Log.d("dev-log", "get all notes: Returning empty value")
        return NoteRepository(null, -1)
    }

    suspend fun updateNote(
        noteUpdateRequest: NoteUpdateRequest
    ): NoteRepository<NoteUpdateResponse> {
        try {
            val noteUpdateResponse = RetrofitInstance.notesApi.updateNote(noteUpdateRequest)
            if (noteUpdateResponse.isSuccessful) {
                val statusCode = noteUpdateResponse.code()
                return NoteRepository(noteUpdateResponse.body(), statusCode)
            } else {
                Log.d("dev-log", "update note: Request unsuccessful")
            }
        } catch (e: java.net.ConnectException) {
            Log.d("dev-log", e.message.toString())
        }
        Log.d("dev-log", "update note: Returning empty value")
        return NoteRepository(null, -1)
    }

    suspend fun uploadNote(
        noteUploadRequest: NoteUploadRequest
    ): NoteRepository<NoteUploadResponse> {
        try {
            val noteUploadResponse = RetrofitInstance.notesApi.uploadNote(noteUploadRequest)
            if (noteUploadResponse.isSuccessful) {
                val statusCode = noteUploadResponse.code()
                return NoteRepository(noteUploadResponse.body(), statusCode)
            } else {
                Log.d("dev-log", "upload note: Request unsuccessful")
            }
        } catch (e: java.net.ConnectException) {
            Log.d("dev-log", e.message.toString())
        }
        Log.d("dev-log", "upload note: Returning empty value")
        return NoteRepository(null, -1)
    }

    suspend fun deleteNote(id: Int): NoteRepository<NoteDeleteResponse> {
        try {
            val noteDeleteResponse = RetrofitInstance.notesApi.deleteNote(id)
            if (noteDeleteResponse.isSuccessful) {
                val statusCode = noteDeleteResponse.code()
                return NoteRepository(noteDeleteResponse.body(), statusCode)
            } else {
                Log.d("dev-log", "delete note: Request unsuccessful")
            }
        } catch (e: java.net.ConnectException) {
            Log.d("dev-log", e.message.toString())
        }
        Log.d("dev-log", "delete note: Returning empty value")
        return NoteRepository(null, -1)
    }
}