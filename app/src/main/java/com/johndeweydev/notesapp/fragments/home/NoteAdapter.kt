package com.johndeweydev.notesapp.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.johndeweydev.notesapp.R
import com.johndeweydev.notesapp.models.Note
import com.johndeweydev.notesapp.models.NoteDateInfo

class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.noteTitle)
    val description: TextView = itemView.findViewById(R.id.noteDescription)
    val cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
}

class NoteAdapter: RecyclerView.Adapter<NoteViewHolder>() {

    private var allNotes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = allNotes[position]

        holder.apply {
            title.text = currentItem.title
            description.text = currentItem.description
            cardView.setOnClickListener {
                val noteInstance = Note(
                    currentItem.id,
                    currentItem.title,
                    currentItem.description,
                    NoteDateInfo(
                        currentItem.dateInfo.created_at,
                        currentItem.dateInfo.updated_at,
                        currentItem.dateInfo.deleted_at
                        )
                )
                val action = HomeDirections.actionHome2ToUpdateNote(noteInstance)
                itemView.findNavController().navigate(action)
            }
        }
    }

    fun appendData(note: Note?) {
        if (note != null) {
            if (note.dateInfo.deleted_at == null) {
                allNotes.add(note)
            }
        }
    }

}