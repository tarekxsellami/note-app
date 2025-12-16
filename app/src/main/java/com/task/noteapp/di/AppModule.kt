package com.task.noteapp.di

import android.app.Application
import androidx.room.Room
import com.task.noteapp.repository.NotelabRepository
import com.task.noteapp.repository.api.INotelabRepository
import com.task.noteapp.repository.database.NotelabDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNotelabDatabase(app: Application): NotelabDatabase =
        Room.databaseBuilder(app, NotelabDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideNotelabRepository(database: NotelabDatabase): INotelabRepository =
        NotelabRepository(database)

    companion object {
        private const val DATABASE_NAME = "notelab_database"
    }
}