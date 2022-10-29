package ru.kpfu.hw_rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    val differ: ItemCallback<MyModel>,
    val onItemClicked: (()-> Unit)?,
    val onDeleteClicked: ((Int)-> Unit)
    ) :
    ListAdapter<MyModel, RecyclerView.ViewHolder>(differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.advertisement_layout -> AdvertisementViewHolder.create(parent)
            R.layout.item_layout -> ItemViewHolder.create(parent, onItemClicked, onDeleteClicked)
            else -> throw IllegalArgumentException("There is no viewHolder for such an item! : )")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is AdvertisementViewHolder -> holder.onBind(item as MyModel.Advertisement)
            is ItemViewHolder -> holder.onBind(item as MyModel.Item)
        }
    }

    override fun getItemViewType(position: Int): Int = 0
//todo

}
