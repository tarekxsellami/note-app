package com.task.noteapp.repository.database

import com.task.noteapp.repository.models.Note
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
class NotesDaoTest : DaoTest() {

    private val dao get() = notelabDatabase.notesDao()

    @Test
    fun test_getAllNotes_after_insertNote() {
        runBlocking {
            dao.insertNote(NOTE_1)
            dao.insertNote(NOTE_2)

            val result = dao.getAllNotes()

            Assert.assertEquals(result, NOTES)
        }
    }

    @Test
    fun test_insertNote_OnConflict() {
        runBlocking {
            val conflictedNote = NOTE_2.copy(createdAt = NOTE_1.createdAt)
            dao.insertNote(NOTE_1)
            dao.insertNote(conflictedNote)

            val result = dao.getAllNotes()

            Assert.assertEquals(result, listOf(conflictedNote))
        }
    }

    @Test
    fun test_deleteNote() {
        runBlocking {
            dao.insertNote(NOTE_1)

            dao.deleteNote(NOTE_1.createdAt)
            val result = dao.getAllNotes()

            Assert.assertEquals(result, emptyList<Note>())
        }
    }

    companion object {
        private val NOTE_1 = Note(
            title = "title",
            description = "description",
            createdAt = 1,
            lastEdit = 0,
            tags = listOf(Tag("TAG_1")),
            imageUrl = "image_url"
        )
        private val NOTE_2 = Note(
            title = "title",
            description = "description",
            createdAt = 2,
            lastEdit = 0,
            tags = listOf(Tag("TAG_2")),
            imageUrl = "image_url"
        )
        private val NOTES = listOf(NOTE_1, NOTE_2)
    }
}
