package ru.kpfu.hw_rv

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class SwipeToDelete(view: View, adapter: MyAdapter?) :
    ItemTouchHelper(object : SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Repository.deleteItem(view.context, viewHolder.adapterPosition)
            adapter?.submitList(Repository.dataList)
        }
    })

