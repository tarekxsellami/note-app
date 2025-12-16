package com.task.noteapp.ui.noteslist

import com.task.noteapp.BaseViewModelTest
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
class NotesListViewModelTest: BaseViewModelTest() {

    private lateinit var viewModel: NotesListViewModel

    @Before
    fun setUp() {
        viewModel = NotesListViewModel(repository)
    }

    @Test
    fun `Assert load loads tags and notes`() {
        runTest {
            viewModel.selectedTags.add(TAG_1)
            coEvery { repository.getAllTags() } returns TAGS
            coEvery { repository.getAllNotes() } returns NOTES

            viewModel.load()

            coVerify { repository.getAllNotes() }
            coVerify { repository.getAllTags() }
            Assert.assertTrue(viewModel.selectedTags.isEmpty())
            Assert.assertEquals(viewModel.notes.value, NOTES)
            Assert.assertEquals(viewModel.tags.value, TAGS)
        }
    }

    @Test
    fun `Test isTagSelected when Tag is selected`() {
        runTest {
            coEvery { repository.getAllTags() } returns TAGS
            coEvery { repository.getAllNotes() } returns NOTES
            viewModel.load()
            viewModel.selectedTags.add(TAG_1)

            viewModel.isTagSelected(TAG_2)

            Assert.assertEquals(viewModel.notesByTag.value!!.toList(), NOTES)
        }
    }

    @Test
    fun `Test isTagSelected when Tag is not selected`() {
        runTest {
            coEvery { repository.getAllTags() } returns TAGS
            coEvery { repository.getAllNotes() } returns NOTES
            viewModel.load()
            viewModel.selectedTags.add(TAG_1)
            viewModel.selectedTags.add(TAG_2)

            viewModel.isTagSelected(TAG_2)

            Assert.assertEquals(viewModel.notesByTag.value!!.toList(), listOf(NOTE_1))
        }
    }

    @Test
    fun `Assert that isTagSelected set back all notes when no tags are selected`() {
        runTest {
            coEvery { repository.getAllTags() } returns TAGS
            coEvery { repository.getAllNotes() } returns NOTES
            viewModel.load()
            viewModel.selectedTags.add(TAG_1)
            viewModel.selectedTags.add(TAG_2)

            viewModel.isTagSelected(TAG_1)
            viewModel.isTagSelected(TAG_2)

            Assert.assertTrue(viewModel.notesByTag.value!!.isEmpty())
        }
    }

    @Test
    fun `Test deleteNote`() {
        runTest {
            viewModel.selectedTags.add(TAG_1)
            coEvery { repository.getAllTags() } returns TAGS
            coEvery { repository.getAllNotes() } returns NOTES
            viewModel.load()
            viewModel.isTagSelected(TAG_1)
            viewModel.isTagSelected(TAG_2)

            viewModel.deleteNote(NOTE_1)

            coVerify { repository.deleteNote(NOTE_1) }
            Assert.assertEquals(viewModel.notes.value, listOf(NOTE_2))
            Assert.assertEquals(viewModel.notesByTag.value, setOf(NOTE_2))
        }
    }
}
