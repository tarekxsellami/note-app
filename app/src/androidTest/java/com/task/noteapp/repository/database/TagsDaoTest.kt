package com.task.noteapp.repository.database

import com.task.noteapp.repository.models.Tag
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
class TagsDaoTest : DaoTest() {

    private val dao get() = notelabDatabase.tagsDao()

    @Test
    fun test_getAllTags_after_insertTag() {
        runBlocking {
            dao.insertTag(TAG_1)
            dao.insertTag(TAG_2)

            val result = dao.getAllTags()

            Assert.assertEquals(result, TAGS)
        }
    }

    @Test
    fun test_getTagById() {
        runBlocking {
            dao.insertTag(TAG_1)
            dao.insertTag(TAG_2)

            val result = dao.getTagById(TAG_1.name)

            Assert.assertEquals(result, TAG_1)
        }
    }

    @Test
    fun test_insertTag_OnConflict() {
        runBlocking {
            val conflictedTag = TAG_2.copy(TAG_1.name)
            dao.insertTag(TAG_1)
            dao.insertTag(conflictedTag)

            val result = dao.getAllTags()

            Assert.assertEquals(result, listOf(conflictedTag))
        }
    }

    @Test
    fun test_deleteTag() {
        runBlocking {
            dao.insertTag(TAG_1)
            dao.deleteTag(TAG_1.name)

            val result = dao.getAllTags()

            Assert.assertEquals(result, listOf<Tag>())
        }
    }

    companion object {
        private val TAG_1 = Tag("TAG_1")
        private val TAG_2 = Tag("TAG_2")
        private val TAGS = listOf(TAG_1, TAG_2)
    }
}
