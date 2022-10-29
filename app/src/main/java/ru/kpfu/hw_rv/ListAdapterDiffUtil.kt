package ru.kpfu.hw_rv

import androidx.recyclerview.widget.DiffUtil

class ListAdapterDiffUtil : DiffUtil.ItemCallback<MyModel>() {
    override fun areItemsTheSame(oldItem: MyModel, newItem: MyModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MyModel, newItem: MyModel): Boolean =
        oldItem == newItem
}