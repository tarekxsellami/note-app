package com.task.noteapp.ui.addtag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.repository.api.INotelabRepository
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
class AddTagViewModel @Inject constructor(private val repository: INotelabRepository) :
    ViewModel() {

    private val _isTagAdded = MutableLiveData<OnTagAdded>()
    val isTagAdded: LiveData<OnTagAdded> get() = _isTagAdded

    fun addTag(tag: Tag) {
        viewModelScope.launch {
            _isTagAdded.value = when {
                repository.getTagById(tag.name) != null -> OnTagAdded.TagAlreadyExists
                tag.name.isEmpty() -> OnTagAdded.EmptyTag
                else -> {
                    repository.addTag(tag)
                    OnTagAdded.TagAddedSuccessfully
                }
            }
        }
    }
}

sealed class OnTagAdded(val message: String) {
    object EmptyTag : OnTagAdded("Tag Name cannot be empty")
    object TagAlreadyExists : OnTagAdded("Tag already exists")
    object TagAddedSuccessfully : OnTagAdded("Tag added successfully")
}
