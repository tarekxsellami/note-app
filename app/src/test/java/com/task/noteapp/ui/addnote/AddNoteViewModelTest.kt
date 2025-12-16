package com.task.noteapp.ui.addnote

import com.task.noteapp.BaseViewModelTest
import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@ExperimentalCoroutinesApi
class AddNoteViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: AddNoteViewModel

    @Before
    fun setUp() {
        viewModel = AddNoteViewModel(repository)
    }

    @Test
    fun `Assert that loadTags retrieves all the tags from the repository`() {
        runTest {
            coEvery { repository.getAllTags() } returns TAGS

            viewModel.loadTags()

            Assert.assertEquals(viewModel.tags.value, TAGS)
        }
    }

    @Test
    fun `Assert that note is added when isEditing is false and note title and description are not empty`() {
        runTest {
            viewModel.addNote(NOTE_1, isEditing = false)

            coVerify { repository.addNote(NOTE_1) }
            Assert.assertTrue(viewModel.isNoteAdded.value is OnNoteAdded.NoteAddedSuccessfully)
        }
    }

    @Test
    fun `Assert that note is edited when isEditing is true and note title and description are not empty`() {
        runTest {
            val note = Note(
                title = "title",
                description = "description",
                createdAt = System.currentTimeMillis(),
                lastEdit = 0,
                tags = TAGS,
                imageUrl = "image_url"
            )

            viewModel.addNote(note, isEditing = true)

            coVerify { repository.addNote(note) }
            Assert.assertTrue(viewModel.isNoteAdded.value is OnNoteAdded.NoteEditedSuccessfully)
        }
    }

    @Test
    fun `Assert that note is not added when note title is empty`() {
        runTest {
            val note = Note(
                title = "",
                description = "description",
                createdAt = System.currentTimeMillis(),
                lastEdit = 0,
                tags = TAGS,
                imageUrl = "image_url"
            )

            viewModel.addNote(note, isEditing = false)

            coVerify(exactly = 0) { repository.addNote(note) }
            Assert.assertTrue(viewModel.isNoteAdded.value is OnNoteAdded.EmptyTitle)
        }
    }

    @Test
    fun `Assert that note is not added when note description is empty`() {
        runTest {
            val note = Note(
                title = "title",
                description = "",
                createdAt = System.currentTimeMillis(),
                lastEdit = 0,
                tags = TAGS,
                imageUrl = "image_url"
            )

            viewModel.addNote(note, isEditing = false)

            coVerify(exactly = 0) { repository.addNote(note) }
            Assert.assertTrue(viewModel.isNoteAdded.value is OnNoteAdded.EmptyDescription)
        }
    }

    @Test
    fun `Test isTagSelected when tag is not already selected`() {
        val result = viewModel.isTagSelected(TAG_1)

        Assert.assertTrue(result)
        Assert.assertEquals(viewModel.selectedTags, listOf(TAG_1))
    }

    @Test
    fun `Test isTagSelected when tag is already selected`() {
        viewModel.isTagSelected(TAG_1)
        val result = viewModel.isTagSelected(TAG_1)

        Assert.assertFalse(result)
        Assert.assertEquals(viewModel.selectedTags, emptyList<Tag>())
    }
}
