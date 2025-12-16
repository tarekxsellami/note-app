package com.task.noteapp.repository.api

import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
interface INotelabRepository {

    suspend fun getAllNotes(): List<Note>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note)

    suspend fun getAllTags(): List<Tag>
    suspend fun addTag(tag: Tag)
    suspend fun deleteTag(tag: Tag)
    suspend fun getTagById(id: String): Tag?
}
