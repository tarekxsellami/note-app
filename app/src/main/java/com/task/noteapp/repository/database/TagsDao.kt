package com.task.noteapp.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.noteapp.repository.models.Tag

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@Dao
interface TagsDao {

    @Query("SELECT * FROM tags")
    suspend fun getAllTags(): List<Tag>

    @Query("SELECT * FROM tags WHERE name = :id")
    suspend fun getTagById(id: String): Tag?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag)

    @Query("DELETE FROM tags WHERE name = :id")
    suspend fun deleteTag(id: String)
}
