package com.example.mediatestapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mediatestapp.MediaQueryType
import com.example.mediatestapp.databinding.ItemQueryTypeBinding

class MediaQueryViewHolder(var binding: ItemQueryTypeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: MediaQueryType, iAdapterListener: IAdapterListener) {
        binding.iAdapterListener = iAdapterListener
        binding.queryType = data
    }

}