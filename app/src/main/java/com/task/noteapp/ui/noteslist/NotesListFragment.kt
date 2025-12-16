package com.task.noteapp.ui.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.FragmentNotesListBinding
import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag
import com.task.noteapp.ui.noteslist.adapters.NotesAdapter
import com.task.noteapp.ui.noteslist.adapters.TagsAdapter
import com.task.noteapp.utils.show
import dagger.hilt.android.AndroidEntryPoint

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@AndroidEntryPoint
class NotesListFragment : Fragment(), NotesAdapter.NoteItemListener, TagsAdapter.TagItemListener {

    private var binding: FragmentNotesListBinding? = null
    private val viewModel: NotesListViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.load()
        initNotesAdapter()
        binding?.addNote?.setOnClickListener {
            findNavController().navigate(
                NotesListFragmentDirections.actionNotesListFragmentToAddNoteFragment(
                    null
                )
            )
        }
        viewModel.apply {
            isLoaded.observe(this@NotesListFragment) {
                if (it) {
                    onDataLoaded()
                }
            }
            notesByTag.observe(this@NotesListFragment) {
                if (selectedTags.isEmpty()) {
                    notesAdapter.submitList(viewModel.notes.value)
                } else {
                    notesAdapter.submitList(it.toList())
                }
            }
        }
    }

    private fun onDataLoaded() {
        viewModel.apply {
            binding?.apply {
                if (selectedTags.isEmpty()) {
                    notesAdapter.submitList(notes.value)
                }
                tagGroup.show(!tags.value.isNullOrEmpty())
                noNotesAvailableGroup.show(notes.value.isNullOrEmpty())
                tagsList.apply {
                    adapter = TagsAdapter(this@NotesListFragment, selectedTags).apply {
                        submitList(tags.value!!)
                    }
                    setHasFixedSize(false)
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    private fun initNotesAdapter() {
        binding?.notesList?.apply {
            notesAdapter = NotesAdapter(requireContext(), this@NotesListFragment)
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val note = notesAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.deleteNote(note)
                }

            }).attachToRecyclerView(this)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onNoteClicked(note: Note) {
        findNavController().navigate(
            NotesListFragmentDirections.actionNotesListFragmentToAddNoteFragment(
                note
            )
        )
    }

    override fun isTagSelected(tag: Tag): Boolean {
        viewModel.isTagSelected(tag)
        return viewModel.selectedTags.contains(tag)
    }
}
