package com.johndeweydev.notesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.johndeweydev.notesapp.databinding.FragmentCreateNoteBinding
import com.johndeweydev.notesapp.models.requestModels.NoteUploadRequest
import com.johndeweydev.notesapp.viewmodels.NotesViewModel

class CreateNote : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }

        notesViewModel = ViewModelProvider(requireActivity())[NotesViewModel::class.java]

        binding.inputLayoutTitle.setEndIconOnClickListener {
            binding.inputTitle.text?.clear()
        }

        binding.clearDescriptionText.setOnClickListener {
            binding.inputDescription.text?.clear()
        }

        binding.createNoteButton.setOnClickListener {
            processUserInput()
        }
    }

    private fun processUserInput() {
        val title = binding.inputTitle.text.toString()
        if (binding.inputTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
        }
        var description = ""
        if (!binding.inputDescription.text.isNullOrEmpty()) {
            description = binding.inputDescription.text.toString()
        }
        val noteUploadRequest = NoteUploadRequest(title, description)
        val previousSize = notesViewModel.allNotes.value?.size

        notesViewModel.allNotes.observe(viewLifecycleOwner) {
            if (it?.size == previousSize?.plus(1)) {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
        notesViewModel.uploadNote(noteUploadRequest)
    }
}