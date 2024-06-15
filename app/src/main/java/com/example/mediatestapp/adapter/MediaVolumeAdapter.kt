package com.example.mediatestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mediatestapp.R
import com.example.mediatestapp.databinding.ItemVolumeBinding

class MediaVolumeAdapter(
    private val iAdapterListener: IAdapterListener,
    private var dataList: MutableList<String>
) : RecyclerView.Adapter<MediaVolumeViewHolder>() {

    fun updateDataChanged(dataList: MutableList<String>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaVolumeViewHolder {
        val binding = DataBindingUtil.inflate<ItemVolumeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_volume,
            parent,
            false
        )
        return MediaVolumeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MediaVolumeViewHolder, position: Int) {
        holder.bind(dataList[position], iAdapterListener)
    }

}