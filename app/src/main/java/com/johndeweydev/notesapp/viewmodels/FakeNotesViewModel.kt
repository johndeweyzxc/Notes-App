package com.johndeweydev.notesapp.viewmodels

import com.johndeweydev.notesapp.models.Note
import com.johndeweydev.notesapp.models.NoteDateInfo
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.models.responseModels.NoteDeleteResponse
import com.johndeweydev.notesapp.models.responseModels.NoteGetAllResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUpdateResponse
import com.johndeweydev.notesapp.models.responseModels.NoteUploadResponse
import com.johndeweydev.notesapp.repository.FakeNotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class FakeNotesViewModel(private val fakeNotesRepository: FakeNotesRepository): NotesViewModel {

    val coroutineScope = CoroutineScope(Dispatchers.IO)
    var allNotes: MutableMap<Int, Note> = mutableMapOf()
    var currentHttpStatusCode by Delegates.notNull<Int>()

    override fun getAllNote() {
        coroutineScope.launch {
            val response = fakeNotesRepository.getAllNotes()
            currentHttpStatusCode = response.statusCode
            response.dataModel?.let { handleGetAllResponseBody(it) }
        }
    }

    override fun updateNote(currentNoteData: Note, noteUpdateRequest: NoteUpdateRequest) {
        coroutineScope.launch {
            val response = fakeNotesRepository.updateNote(noteUpdateRequest)
            currentHttpStatusCode = response.statusCode
            response.dataModel?.let { handleUpdateResponseBody(currentNoteData, it) }
        }
    }

    override fun uploadNote(noteUploadRequest: NoteUploadRequest) {
        coroutineScope.launch {
            val response = fakeNotesRepository.uploadNote(noteUploadRequest)
            currentHttpStatusCode = response.statusCode
            response.dataModel?.let { handleUploadResponseBody(it) }
        }
    }

    override fun deleteNote(id: Int) {
        coroutineScope.launch {
            val response = fakeNotesRepository.deleteNote(id)
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

        allNotes = firstCopyOfAllNotes
    }

    private fun handleUploadResponseBody(responseBody: NoteUploadResponse) {
        val noteId = responseBody.id
        val title = responseBody.title
        val description = responseBody.description
        val dateInfo = NoteDateInfo(responseBody.created_at, null, null)

        val copyOfAllNotes = allNotes
        val note = Note(noteId, title, description, dateInfo)
        copyOfAllNotes[noteId] = note

        allNotes = copyOfAllNotes
    }

    private fun handleUpdateResponseBody(currentNoteData: Note, responseBody: NoteUpdateResponse) {
        val noteId = responseBody.id
        val title = responseBody.title
        val description = responseBody.description
        val updatedAt = responseBody.updated_at

        val createdAt = currentNoteData.dateInfo.created_at
        val dateInfo = NoteDateInfo(createdAt, updatedAt, null)
        val note = Note(noteId, title, description, dateInfo)

        val copyOfAllNotes = allNotes
        copyOfAllNotes[noteId] = note
        allNotes = copyOfAllNotes
    }

    private fun handleDeleteResponseBody(id: Int, responseBody: NoteDeleteResponse) {
        val deletedAt = responseBody.deleted_at
        val copyOfAllNotes = allNotes
        copyOfAllNotes[id]?.dateInfo?.deleted_at = deletedAt
        allNotes = copyOfAllNotes
    }
}