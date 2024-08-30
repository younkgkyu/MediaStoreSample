package com.example.mediatestapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mediatestapp.databinding.ItemMediaBinding

class MediaListViewHolder(var binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String, iAdapterListener: IAdapterListener, id: Long) {
        binding.title = data
        binding.iAdapterListener = iAdapterListener
        binding.id = id
    }

}