package com.task.noteapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
object Utils {

    fun getDateFromMillis(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(millis)
    }
}
