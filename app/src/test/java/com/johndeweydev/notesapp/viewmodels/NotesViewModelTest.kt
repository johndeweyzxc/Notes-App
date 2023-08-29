package com.johndeweydev.notesapp.viewmodels

import com.google.common.truth.Truth.assertThat
import com.johndeweydev.notesapp.models.Note
import com.johndeweydev.notesapp.models.NoteDateInfo
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.models.responseModels.NoteGetResponse
import com.johndeweydev.notesapp.repository.FakeNotesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NotesViewModelTest {

    private lateinit var viewModel: FakeNotesViewModel

    private fun fakeNotes(): MutableList<NoteGetResponse> {
        val listOfNoteGetResponse = mutableListOf<NoteGetResponse>()
        listOfNoteGetResponse.add(
            NoteGetResponse(1, "Title1", "Description1",
                NoteDateInfo(1, null, null)
            )
        )
        listOfNoteGetResponse.add(
            NoteGetResponse(2, "Title2", "Description2",
                NoteDateInfo(2, null, null)
            )
        )
        listOfNoteGetResponse.add(
            NoteGetResponse(3, "Title3", "Description3",
                NoteDateInfo(3, null, null)
            )
        )

        return listOfNoteGetResponse
    }

    @Before
    fun setup() {
        val repository = FakeNotesRepository()
        repository.listOfNoteGetResponse = fakeNotes()
        viewModel = FakeNotesViewModel(repository)
        viewModel.getAllNote()
    }

    private fun verifyUploadedNote(title: String, description: String): Boolean {
        for (value in viewModel.allNotes.values) {
            if (value.title == title && value.description == description) {
                return true
            } else {
                continue
            }
        }
        return false
    }

    private fun uploadNote(title: String, description: String) = runTest {
        val noteUploadRequest = NoteUploadRequest(title, description)
        viewModel.uploadNote(noteUploadRequest)
    }

    @Test
    fun `upload a note`() {
        val title = "Title4"
        val description = "Description4"
        uploadNote("Title4", "Description4")
        assertThat(verifyUploadedNote(title, description)).isTrue()
    }

    private fun verifyUpdatedNote(
        id: Int,
        createdAtDate: Int,
        updatedTitle:
        String,
        updatedDescription: String
    ): Boolean {
        val getUpdatedNote = viewModel.allNotes[id]

        val title = getUpdatedNote?.title == updatedTitle
        val description = getUpdatedNote?.description == updatedDescription
        val updatedAt = getUpdatedNote?.dateInfo?.updated_at != null
        val createdAt = getUpdatedNote?.dateInfo?.created_at == createdAtDate

        if (title && description && updatedAt && createdAt) {
            return true
        }
        return false
    }

    private fun updateNote(
        currentNoteData: Note,
        id: Int, title: String,
        description: String
    ) = runTest {
        val noteUpdateRequest = NoteUpdateRequest(id, title, description)
        viewModel.updateNote(currentNoteData, noteUpdateRequest)
    }

    @Test
    fun `update a note`() {
        val noteId = 3
        val createdAt = 3

        val currentNoteData = Note(noteId, "Title3", "Description3",
            NoteDateInfo(createdAt, null, null)
        )
        val updatedTitle = "UpdatedTitle3"
        val updatedDescription = "UpdatedDescription3"
        updateNote(currentNoteData, noteId, updatedTitle, updatedDescription)
        assertThat(verifyUpdatedNote(noteId, createdAt, updatedTitle, updatedDescription)).isTrue()
    }
}