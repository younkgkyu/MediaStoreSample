package com.example.mediatestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mediatestapp.MediaQueryType
import com.example.mediatestapp.R
import com.example.mediatestapp.databinding.ItemQueryTypeBinding
import com.example.mediatestapp.databinding.ItemVolumeBinding

class MediaQueryAdapter(
    private val iAdapterListener: IAdapterListener,
    private var dataList: MutableList<MediaQueryType>
) : RecyclerView.Adapter<MediaQueryViewHolder>() {

    fun updateDataChanged(dataList: MutableList<MediaQueryType>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaQueryViewHolder {
        val binding = DataBindingUtil.inflate<ItemQueryTypeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_query_type,
            parent,
            false
        )
        return MediaQueryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MediaQueryViewHolder, position: Int) {
        holder.bind(dataList[position], iAdapterListener)
    }

}