package com.task.noteapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.noteapp.repository.converters.Converters
import com.task.noteapp.repository.models.Note
import com.task.noteapp.repository.models.Tag

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@Database(
    entities = [Note::class, Tag::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NotelabDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun tagsDao(): TagsDao
}
