package com.task.noteapp.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentAddNoteBinding
import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag
import com.task.noteapp.ui.addtag.AddTagBottomSheetDialog
import com.task.noteapp.ui.noteslist.adapters.TagsAdapter
import com.task.noteapp.utils.getContent
import com.task.noteapp.utils.show
import dagger.hilt.android.AndroidEntryPoint

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@AndroidEntryPoint
class AddNoteFragment : Fragment(), TagsAdapter.TagItemListener,
    AddTagBottomSheetDialog.OnDismissListener {

    private var binding: FragmentAddNoteBinding? = null
    private val viewModel: AddNoteViewModel by viewModels()
    private val navArgs: AddNoteFragmentArgs by navArgs()
    private val isEditingNote get() = navArgs.note != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditableNote()
        binding?.apply {
            addNote.text =
                if (isEditingNote) getString(R.string.edit_note) else getString(R.string.add_note)
            addNote.setOnClickListener {
                val note = Note(
                    createdAt = if (isEditingNote) navArgs.note!!.createdAt else System.currentTimeMillis(),
                    title = noteTitle.getContent(),
                    description = noteDescription.getContent(),
                    imageUrl = imageUrl.getContent(),
                    tags = viewModel.selectedTags,
                    lastEdit = if (isEditingNote) System.currentTimeMillis() else 0L
                )
                viewModel.addNote(note, isEditingNote)
            }
            addTags.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(AddTagBottomSheetDialog(this@AddNoteFragment), AddTagBottomSheetDialog.TAG)
                    .commit()
            }
        }
        viewModel.apply {
            isNoteAdded.observe(this@AddNoteFragment) {
                Snackbar.make(requireView(), getString(it.message), Snackbar.LENGTH_SHORT).show()
            }
            tags.observe(this@AddNoteFragment) {
                binding?.tagGroup?.show(it.isNotEmpty())
                binding?.tags?.apply {
                    adapter = TagsAdapter(this@AddNoteFragment, selectedTags).apply {
                        submitList(it)
                    }
                    setHasFixedSize(false)
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun initEditableNote() {
        val note = navArgs.note?.apply {
            viewModel.selectedTags = tags.toMutableList()
        }
        binding?.apply {
            noteTitle.setText(note?.title)
            noteDescription.setText(note?.description)
            imageUrl.setText(note?.imageUrl)
        }
    }

    override fun isTagSelected(tag: Tag): Boolean {
        return viewModel.isTagSelected(tag)
    }

    override fun onDismissed() {
        viewModel.loadTags()
    }
}
