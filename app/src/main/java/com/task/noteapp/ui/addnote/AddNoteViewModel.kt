package com.task.noteapp.ui.addnote

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.R
import com.task.noteapp.repository.api.INotelabRepository
import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@HiltViewModel
class AddNoteViewModel @Inject constructor(private val repository: INotelabRepository) :
    ViewModel() {

    private val _isNoteAdded = MutableLiveData<OnNoteAdded>()
    val isNoteAdded: LiveData<OnNoteAdded> get() = _isNoteAdded

    private val _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> get() = _tags

    var selectedTags = mutableListOf<Tag>()

    init {
        loadTags()
    }

    fun loadTags() {
        viewModelScope.launch {
            _tags.value = repository.getAllTags()
        }
    }

    fun addNote(note: Note, isEditing: Boolean) {
        viewModelScope.launch {
            _isNoteAdded.value = when {
                note.title.trim().isEmpty() -> OnNoteAdded.EmptyTitle
                note.description.trim().isEmpty() -> OnNoteAdded.EmptyDescription
                isEditing -> {
                    repository.addNote(note)
                    OnNoteAdded.NoteEditedSuccessfully
                }
                else -> {
                    repository.addNote(note)
                    OnNoteAdded.NoteAddedSuccessfully
                }
            }
        }
    }

    fun isTagSelected(tag: Tag): Boolean {
        return if (tag in selectedTags) {
            selectedTags.remove(tag)
            false
        } else {
            selectedTags.add(tag)
            true
        }
    }
}

sealed class OnNoteAdded(@StringRes val message: Int) {
    object EmptyTitle : OnNoteAdded(R.string.note_title_cannot_be_empty)
    object EmptyDescription : OnNoteAdded(R.string.note_description_cannot_be_empty)
    object NoteAddedSuccessfully : OnNoteAdded(R.string.note_added_successfully)
    object NoteEditedSuccessfully : OnNoteAdded(R.string.note_edit_successfully)
}
