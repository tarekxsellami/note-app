package com.task.noteapp.ui.addtag

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.task.noteapp.databinding.FragmentAddTagBottomSheetDialogBinding
import com.task.noteapp.repository.models.Tag
import com.task.noteapp.utils.getContent
import dagger.hilt.android.AndroidEntryPoint

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
@AndroidEntryPoint
class AddTagBottomSheetDialog(private val listener: OnDismissListener) :
    BottomSheetDialogFragment() {

    interface OnDismissListener {
        fun onDismissed()
    }

    private var binding: FragmentAddTagBottomSheetDialogBinding? = null
    private val viewModel: AddTagViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTagBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            addTag.setOnClickListener {
                viewModel.addTag(Tag(tagName.getContent().lowercase()))
            }
        }
        viewModel.isTagAdded.observe(this) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            if (it is OnTagAdded.TagAddedSuccessfully) {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        listener.onDismissed()
        super.onDismiss(dialog)
    }

    companion object {
        const val TAG = "AddTagBottomSheetDialog.tag"
    }
}
