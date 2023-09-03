package com.harutyun.androidfirebasedemo.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harutyun.androidfirebasedemo.databinding.ItemTextBinding
import com.harutyun.domain.models.Item

internal class ListItemAdapter : ListAdapter<Item, ListItemAdapter.ItemViewHolder>(
    AsyncDifferConfig.Builder(DiffCallback()).build()
) {
    inner class ItemViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.tvText.text = item.text
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

internal class DiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item) =
        oldItem == newItem

}