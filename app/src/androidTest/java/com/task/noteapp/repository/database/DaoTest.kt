package com.task.noteapp.repository.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
open class DaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    protected lateinit var notelabDatabase: NotelabDatabase

    @Before
    open fun setUp() {
        notelabDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotelabDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        notelabDatabase.close()
    }
}
