package com.johndeweydev.notesapp.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndeweydev.notesapp.R
import com.johndeweydev.notesapp.databinding.FragmentHomeBinding
import com.johndeweydev.notesapp.models.Note
import com.johndeweydev.notesapp.viewmodels.NotesViewModel

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var itemListAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_home2_to_createNote)
        }

        notesViewModel = ViewModelProvider(requireActivity())[NotesViewModel::class.java]

        itemListAdapter = NoteAdapter()
        binding.recyclerView.adapter = itemListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        notesViewModel.allNotes.observe(viewLifecycleOwner) {
            handleUpdateFromLiveData(it)
        }
    }

    private fun handleUpdateFromLiveData(it: MutableMap<Int, Note>?) {
        val code = notesViewModel.currentHttpStatusCode
        if (code == 200 || code == 201) {
            for ((_, value) in it!!) {
                itemListAdapter.appendData(value)
                itemListAdapter.notifyItemInserted(itemListAdapter.itemCount)
            }
        } else {
            Toast.makeText(context, "Internal server error", Toast.LENGTH_SHORT).show()
        }
    }

}