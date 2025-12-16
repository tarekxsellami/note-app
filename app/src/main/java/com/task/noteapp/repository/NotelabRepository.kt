package com.task.noteapp.repository

import com.task.noteapp.repository.api.INotelabRepository
import com.task.noteapp.repository.database.NotelabDatabase
import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag
import javax.inject.Inject

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
class NotelabRepository @Inject constructor(private val database: NotelabDatabase) :
    INotelabRepository {
    override suspend fun getAllNotes(): List<Note> =
        database.notesDao().getAllNotes()

    override suspend fun addNote(note: Note) =
        database.notesDao().insertNote(note)

    override suspend fun deleteNote(note: Note) =
        database.notesDao().deleteNote(note.createdAt)

    override suspend fun getAllTags(): List<Tag> =
        database.tagsDao().getAllTags()

    override suspend fun getTagById(id: String): Tag? =
        database.tagsDao().getTagById(id)


    override suspend fun addTag(tag: Tag) =
        database.tagsDao().insertTag(tag)

    override suspend fun deleteTag(tag: Tag) =
        database.tagsDao().deleteTag(tag.name)
}
