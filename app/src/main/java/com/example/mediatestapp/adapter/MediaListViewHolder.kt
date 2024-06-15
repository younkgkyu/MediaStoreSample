package com.example.mediatestapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mediatestapp.databinding.ItemMediaBinding

class MediaListViewHolder(var binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String) {
        binding.title = data
    }

}