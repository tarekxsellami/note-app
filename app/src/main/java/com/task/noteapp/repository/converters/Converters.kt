package com.task.noteapp.repository.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.task.noteapp.repository.models.Tag

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
class Converters {

    @TypeConverter
    fun fromList(value: List<Tag>): String = Gson().toJson(value)

    @TypeConverter
    fun fromString(value: String) = Gson().fromJson(value, Array<Tag>::class.java).toList()
}
