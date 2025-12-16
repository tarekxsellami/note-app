package com.task.noteapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.noteapp.repository.api.INotelabRepository
import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
open class BaseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    protected val repository: INotelabRepository = mockk(relaxed = true)

    companion object {
        val TAG_1 = Tag("TAG_1")
        val TAG_2 = Tag("TAG_2")
        val TAGS = listOf(TAG_1, TAG_2)
        val NOTE_1 = Note(
            title = "title",
            description = "description",
            createdAt = System.currentTimeMillis(),
            lastEdit = 0,
            tags = listOf(TAG_1),
            imageUrl = "image_url"
        )
        val NOTE_2 = Note(
            title = "title",
            description = "description",
            createdAt = System.currentTimeMillis(),
            lastEdit = 0,
            tags = listOf(TAG_2),
            imageUrl = "image_url"
        )
        val NOTES = listOf(NOTE_1, NOTE_2)
    }
}
