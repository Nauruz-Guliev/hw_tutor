package ru.kpfu.hw_rv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.hw_rv.databinding.ItemLayoutBinding

class ItemViewHolder(
    val binding: ItemLayoutBinding,
    val onItemClicked: (() -> Unit)?,
    val onDeleteClicked: ((Int) -> Unit)?,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
          //todo
        }
    }

    fun onBind(item: MyModel.Item) {
        with(binding) {
            tvTitle.text = item.title
            tvDescription.text = item.description
        }
    }


    companion object {
        fun create(
            parent: ViewGroup,
            onItemClicked: (() -> Unit)?,
            onDeleteClicked: ((Int) -> Unit)?
        ): ItemViewHolder = ItemViewHolder(
            binding = ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked = onItemClicked,
            onDeleteClicked = onDeleteClicked,
        )
    }
}