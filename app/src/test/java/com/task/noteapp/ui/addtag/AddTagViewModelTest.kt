package com.task.noteapp.ui.addtag

import com.task.noteapp.BaseViewModelTest
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
class AddTagViewModelTest: BaseViewModelTest() {

    private lateinit var viewModel: AddTagViewModel

    @Before
    fun setUp() {
        viewModel = AddTagViewModel(repository)
    }

    @Test
    fun `Test addTag when Tag is not already added and name is not empty`() {
        runTest {
            coEvery { repository.getTagById(TAG_1.name) } returns null

            viewModel.addTag(TAG_1)

            coVerify { repository.addTag(TAG_1) }
            Assert.assertTrue(viewModel.isTagAdded.value is OnTagAdded.TagAddedSuccessfully)
        }
    }

    @Test
    fun `Test addTag when Tag is already added and name is not empty`() {
        runTest {
            coEvery { repository.getTagById(TAG_1.name) } returns TAG_1

            viewModel.addTag(TAG_1)

            coVerify(exactly = 0) { repository.addTag(TAG_1) }
            Assert.assertTrue(viewModel.isTagAdded.value is OnTagAdded.TagAlreadyExists)
        }
    }

    @Test
    fun `Test addTag when Tag is not already added and name is empty`() {
        runTest {
            coEvery { repository.getTagById("") } returns null

            viewModel.addTag(Tag(""))

            coVerify(exactly = 0) { repository.addTag(TAG_1) }
            Assert.assertTrue(viewModel.isTagAdded.value is OnTagAdded.EmptyTag)
        }
    }
}
