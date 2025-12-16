package com.task.noteapp.repository.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey val createdAt: Long = System.currentTimeMillis(),
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val tags: List<Tag> = emptyList(),
    val lastEdit: Long = 0L
) : Parcelable
