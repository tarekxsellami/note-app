package com.task.noteapp.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.repository.api.INotelabRepository
import com.task.noteapp.repository.models.Note
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
class OnBoardingViewModel @Inject constructor(private val repository: INotelabRepository) :
    ViewModel() {

    fun initiateOnBoardingNote(notes: List<Note>) {
        viewModelScope.launch {
            notes.forEach {
                repository.addNote(it)
            }
        }
    }
}
