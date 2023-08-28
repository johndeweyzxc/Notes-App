package com.johndeweydev.notesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.johndeweydev.notesapp.databinding.FragmentUpdateNoteBinding
import com.johndeweydev.notesapp.models.requestModels.NoteUpdateRequest
import com.johndeweydev.notesapp.viewmodels.NotesViewModel

class UpdateNote : Fragment() {

    private lateinit var binding: FragmentUpdateNoteBinding
    private lateinit var notesViewModel: NotesViewModel
    private val args by navArgs<UpdateNoteArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
        notesViewModel = ViewModelProvider(requireActivity())[NotesViewModel::class.java]
        binding.inputTitle.setText(args.currentNote.title)
        binding.inputDescription.setText(args.currentNote.description)

        binding.inputLayoutTitle.setEndIconOnClickListener {
            binding.inputTitle.text?.clear()
        }

        binding.clearDescriptionText.setOnClickListener {
            binding.inputDescription.text?.clear()
        }

        binding.appBar.menu[0].setOnMenuItemClickListener {
            deleteUser()
            true
        }

        binding.updateNoteButton.setOnClickListener {
            updateUser()
        }
    }

    private fun updateUser() {
        val noteId = args.currentNote.id
        val title = binding.inputTitle.text.toString()
        if (binding.inputTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val description = binding.inputDescription.text.toString()
        val currentTitle = args.currentNote.title
        val currentDescription = args.currentNote.description
        if (title == currentTitle && description == currentDescription) {
            Toast.makeText(context, "No changes have been made", Toast.LENGTH_SHORT).show()
            return
        }

        val noteUpdateRequest = NoteUpdateRequest(noteId, title, description)
        val previousDateOfUpdatedAt = args.currentNote.dateInfo.updated_at

        notesViewModel.allNotes.observe(viewLifecycleOwner) {
            if (it != null) {
                val newDateForUpdatedAt = it[noteId]?.dateInfo?.updated_at

                if (previousDateOfUpdatedAt == null && newDateForUpdatedAt != null) {
                    Navigation.findNavController(binding.root).popBackStack()
                } else if(previousDateOfUpdatedAt != null
                    && (newDateForUpdatedAt!! > previousDateOfUpdatedAt)) {
                    Navigation.findNavController(binding.root).popBackStack()
                }
            }
        }
        notesViewModel.updateNote(args.currentNote, noteUpdateRequest)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())

        val previousDateOfDeletedAt = args.currentNote.dateInfo.deleted_at
        val noteId = args.currentNote.id
        notesViewModel.allNotes.observe(viewLifecycleOwner) {
            if (it != null) {
                val newDateForDeletedAt = it[noteId]?.dateInfo?.deleted_at
                if (previousDateOfDeletedAt == null && newDateForDeletedAt != null) {
                    Navigation.findNavController(binding.root).popBackStack()
                }
            }
        }

        builder.setPositiveButton("Yes") { _, _ ->
            notesViewModel.deleteNote(noteId)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete ${args.currentNote.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentNote.title}?")
        builder.create().show()
    }
}