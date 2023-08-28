package com.johndeweydev.notesapp.api

import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.models.responseModels.NoteDeleteResponse
import com.johndeweydev.notesapp.models.responseModels.NoteGetAllResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUpdateResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUploadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NotesApi {

    @GET("/api/v1/get-all-note")
    suspend fun getAllNotes(): Response<NoteGetAllResponse>

    @PATCH("/api/v1/update-note")
    suspend fun updateNote(
        @Body noteUpdateRequest: NoteUpdateRequest
    ): Response<NoteUpdateResponse>

    @POST("/api/v1/upload-note")
    suspend fun uploadNote(
        @Body noteUploadRequest: NoteUploadRequest
    ): Response<NoteUploadResponse>

    @DELETE("/api/v1/delete-note/{id}")
    suspend fun deleteNote(
        @Path("id") id: Int
    ): Response<NoteDeleteResponse>
}