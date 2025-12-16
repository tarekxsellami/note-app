package com.task.noteapp.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class NotesListViewModel @Inject constructor(private val notelabRepository: INotelabRepository) :
    ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    private val _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> get() = _tags

    val selectedTags = mutableListOf<Tag>()

    private val _notesByTag = MutableLiveData<Set<Note>>()
    val notesByTag: LiveData<Set<Note>> get() = _notesByTag


    val isLoaded = MediatorLiveData<Boolean>().apply {
        addSource(_notes) {
            _tags.value?.let {
                value = true
            } ?: run {
                value = false
            }
        }
        addSource(_tags) {
            _notes.value?.let {
                value = true
            } ?: run {
                value = false
            }
        }
    }

    fun load() {
        viewModelScope.launch {
            selectedTags.clear()
            _notes.value = notelabRepository.getAllNotes()
            _tags.value = notelabRepository.getAllTags()
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notelabRepository.deleteNote(note)
            _notes.value = _notes.value?.filter { it != note }
            _notesByTag.value = _notesByTag.value?.filter { it != note }?.toSet()
        }
    }

    fun isTagSelected(tag: Tag) {
        viewModelScope.launch {
            if (tag in selectedTags) {
                selectedTags.remove(tag)
            } else {
                selectedTags.add(tag)
            }
            if (selectedTags.isEmpty()) {
                _notesByTag.value = emptySet()
            } else {
                _notesByTag.value =
                    notes.value?.filter { it.tags.intersect(selectedTags).isNotEmpty() }?.toSet()
            }
        }
    }
}
