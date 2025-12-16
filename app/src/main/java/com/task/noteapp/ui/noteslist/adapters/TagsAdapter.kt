package com.task.noteapp.ui.noteslist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.TagLayoutBinding
import com.task.noteapp.repository.models.Tag

/**
 *  Created by Tarek Sellami (tarek_sellami@hotmail.com) on 09.04.22.
 *
 *  Copyright (c) 2022
 *  all rights reserved
 */
class TagsAdapter(
    private val listener: TagItemListener,
    private val selectedTags: List<Tag>
) : ListAdapter<Tag, TagsAdapter.TagViewHolder>(DiffComparator()) {

    interface TagItemListener {
        fun isTagSelected(tag: Tag): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = TagLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class TagViewHolder(private val binding: TagLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION && currentList[layoutPosition] != null) {
                        setTagSelected(listener.isTagSelected(currentList[layoutPosition]))
                    }
                }
            }
        }

        fun bind(tag: Tag?) {
            binding.tagName.text = tag?.name
            setTagSelected(tag in selectedTags)
        }

        private fun setTagSelected(isSelected: Boolean) {
            binding.tagName.alpha = if (isSelected) 1f else 0.3f
        }
    }

    class DiffComparator : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(
            oldItem: Tag,
            newItem: Tag
        ): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: Tag,
            newItem: Tag
        ): Boolean =
            oldItem == newItem
    }
}
