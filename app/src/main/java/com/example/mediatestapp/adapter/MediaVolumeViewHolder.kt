package com.example.mediatestapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mediatestapp.databinding.ItemVolumeBinding

class MediaVolumeViewHolder(var binding: ItemVolumeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String, iAdapterListener: IAdapterListener) {
        binding.iAdapterListener = iAdapterListener
        binding.volume = data
    }

}