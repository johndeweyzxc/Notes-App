package com.johndeweydev.notesapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndeweydev.notesapp.models.Note
import com.johndeweydev.notesapp.models.NoteDateInfo
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.models.responseModels.NoteDeleteResponse
import com.johndeweydev.notesapp.models.responseModels.NoteGetAllResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUpdateResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUploadResponse
import com.johndeweydev.notesapp.repository.DefaultNotesRepository
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class DefaultNotesViewModel(
    private val defaultNotesRepository: DefaultNotesRepository
    ): ViewModel(), NotesViewModel {

    var currentHttpStatusCode by Delegates.notNull<Int>()
    val allNotes: MutableLiveData<MutableMap<Int, Note>?> = MutableLiveData()

    init {
        getAllNote()
    }

    override fun getAllNote() {
        viewModelScope.launch {
            val response = defaultNotesRepository.getAllNotes()
            currentHttpStatusCode = response.statusCode
            if (currentHttpStatusCode != -1) {
                response.dataModel?.let { handleGetAllResponseBody(it) }
            }
        }
    }

    override fun updateNote(currentNoteData: Note, noteUpdateRequest: NoteUpdateRequest) {
        viewModelScope.launch {
            val response = defaultNotesRepository.updateNote(noteUpdateRequest)
            currentHttpStatusCode = response.statusCode
            if (currentHttpStatusCode != -1) {
                response.dataModel?.let { handleUpdateResponseBody(currentNoteData, it) }
            }
        }
    }

    override fun uploadNote(noteUploadRequest: NoteUploadRequest) {
        viewModelScope.launch {
            val response = defaultNotesRepository.uploadNote(noteUploadRequest)
            currentHttpStatusCode = response.statusCode
            if (currentHttpStatusCode != -1) {
                response.dataModel?.let { handleUploadResponseBody(it) }
            }
        }
    }

    override fun deleteNote(id: Int) {
        viewModelScope.launch {
            val response = defaultNotesRepository.deleteNote(id)
            currentHttpStatusCode = response.statusCode
            response.dataModel?.let { handleDeleteResponseBody(id, it) }
        }
    }

    private fun handleGetAllResponseBody(responseBody: NoteGetAllResponse) {
        val list = responseBody.note_list
        val firstCopyOfAllNotes = mutableMapOf<Int, Note>()

        for (note in list) {
            val dateInfo = note.date_information
            val date = NoteDateInfo(
                dateInfo.created_at,
                dateInfo.updated_at,
                dateInfo.deleted_at
            )
            val convertToNote = Note(note.id, note.title, note.description, date)
            firstCopyOfAllNotes[note.id] = convertToNote
        }

        allNotes.value = firstCopyOfAllNotes
    }

    private fun handleUploadResponseBody(responseBody: NoteUploadResponse) {
        val noteId = responseBody.id
        val title = responseBody.title
        val description = responseBody.description
        val dateInfo = NoteDateInfo(responseBody.created_at, null, null)

        val copyOfAllNotes = allNotes.value
        val note = Note(noteId, title, description, dateInfo)
        copyOfAllNotes?.put(responseBody.id, note)
        Log.d("dev-log", "Data uploaded")
        allNotes.value = copyOfAllNotes
    }

    private fun handleUpdateResponseBody(
        currentNoteData: Note,
        responseBody: NoteUpdateResponse
    ) {
        val noteId = responseBody.id
        val title = responseBody.title
        val description = responseBody.description
        val updatedAt = responseBody.updated_at

        val createdAt = currentNoteData.dateInfo.created_at
        val dateInfo = NoteDateInfo(createdAt, updatedAt, null)
        val note = Note(noteId, title, description, dateInfo)

        val copyOfAllNotes = allNotes.value
        copyOfAllNotes?.set(noteId, note)
        allNotes.value = copyOfAllNotes
    }

    private fun handleDeleteResponseBody(id: Int, responseBody: NoteDeleteResponse) {
        val deletedAt = responseBody.deleted_at
        val copyOfAllNotes = allNotes.value
        copyOfAllNotes?.get(id)?.dateInfo?.deleted_at = deletedAt
        allNotes.value = copyOfAllNotes
    }
}