package ru.kpfu.itis

import android.util.Log
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

class DiffCallback : DiffUtil.ItemCallback<DataModel>() {
    override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        val isSameFirst = oldItem is DataModel.FirstType && newItem is DataModel.FirstType
        val isSameSecond = oldItem is DataModel.SecondType && newItem is DataModel.SecondType
        val isSameThird = oldItem is DataModel.ThirdType && newItem is DataModel.ThirdType
        Log.d("value1", newItem.id.toString())
        Log.d("value2", oldItem.id.toString())
        return isSameFirst || isSameSecond || isSameThird
    }

    override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem == newItem
    }
}
