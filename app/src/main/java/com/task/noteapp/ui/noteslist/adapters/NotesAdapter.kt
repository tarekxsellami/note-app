package com.task.noteapp.ui.noteslist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.databinding.NoteLayoutBinding
import com.task.noteapp.repository.models.Note
import com.task.noteapp.utils.Utils
import com.task.noteapp.utils.gone
import com.task.noteapp.utils.setGlideResource
import com.task.noteapp.utils.show

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
class NotesAdapter(
    private val context: Context,
    private val listener: NoteItemListener,
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DiffComparator()) {

    interface NoteItemListener {
        fun onNoteClicked(note: Note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class NoteViewHolder(private val binding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onNoteClicked(currentList[layoutPosition])
                }
            }
        }

        fun bind(note: Note) {
            binding.apply {
                noteTitle.text = note.title
                noteDescription.text = note.description
                createdAt.text =
                    context.getString(R.string.created_at, Utils.getDateFromMillis(note.createdAt))
                if (note.lastEdit > 0) {
                    editedAt.show()
                    editedAt.text = context.getString(
                        R.string.edited_at,
                        Utils.getDateFromMillis(note.lastEdit)
                    )
                } else {
                    editedAt.gone()
                }
                if (!note.imageUrl.isNullOrEmpty()) {
                    noteImage.setGlideResource(note.imageUrl)
                    noteImage.show()
                } else {
                    noteImage.gone()
                }
            }
        }
    }

    class DiffComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean =
            oldItem.createdAt == newItem.createdAt

        override fun areContentsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean =
            oldItem == newItem
    }
}
