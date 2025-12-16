package com.task.noteapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.task.noteapp.databinding.ActivityMainBinding
import com.task.noteapp.ui.onboarding.ADD_NOTE_SHORTCUT
import dagger.hilt.android.AndroidEntryPoint

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.navigation_graph)
        graph.startDestination = if (getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                .getBoolean(ON_BOARDING_KEY, false)
        )
            R.id.notesListFragment
        else
            R.id.welcome
        navController.graph = graph
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.welcome, R.id.notesListFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        when (intent.action) {
            ADD_NOTE_SHORTCUT -> navController.navigate(R.id.addNoteFragment)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val SHARED_PREF_KEY = "SHARED_PREF_KEY"
        const val ON_BOARDING_KEY = "ON_BOARDING_KEY"
    }
}
