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
@Entity(tableName = "tags")
@Parcelize
data class Tag(
    @PrimaryKey val name: String
) : Parcelable
