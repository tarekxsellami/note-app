package com.task.noteapp.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.task.noteapp.MainActivity
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentOnBoardingBinding
import com.task.noteapp.repository.models.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private var binding: FragmentOnBoardingBinding? = null
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            requireActivity().getSharedPreferences(
                MainActivity.SHARED_PREF_KEY,
                Context.MODE_PRIVATE
            ).edit().putBoolean(MainActivity.ON_BOARDING_KEY, true).apply()
            val swipeToDeleteNote = Note(
                title = requireContext().getString(R.string.delete_note_title),
                description = requireContext().getString(R.string.delete_note_description)
            )
            val imageNote = Note(
                createdAt = System.currentTimeMillis() + 2000,
                title = requireContext().getString(R.string.photo_note_title),
                description = requireContext().getString(R.string.photo_note_description),
                imageUrl = "https://images.unsplash.com/photo-1514845505178-849cebf1a91d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80"
            )
            viewModel.initiateOnBoardingNote(listOf(swipeToDeleteNote, imageNote))

        }
        binding?.start?.setOnClickListener {
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToNotesListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
