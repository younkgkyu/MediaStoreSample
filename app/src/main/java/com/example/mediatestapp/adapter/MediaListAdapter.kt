package com.example.mediatestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mediatestapp.R
import com.example.mediatestapp.databinding.ItemMediaBinding

class MediaListAdapter(
    private var dataList: List<String>,
    private val iAdapterListener: IAdapterListener,
    private var idList: List<Long> = listOf()
) : RecyclerView.Adapter<MediaListViewHolder>() {

    fun updateDataChanged(dataList: List<String>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun updateDataChanged(dataList: List<String>, idList: List<Long>) {
        this.dataList = dataList
        this.idList = idList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListViewHolder {
        val binding = DataBindingUtil.inflate<ItemMediaBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_media,
            parent,
            false
        )
        return MediaListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MediaListViewHolder, position: Int) {
        holder.bind(dataList[position], iAdapterListener, idList[position])
    }

}