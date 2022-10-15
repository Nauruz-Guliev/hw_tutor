package ru.kpfu.itis.hwrecyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecoration(private val divider: Drawable, private val spacingDP: Int) :
    RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + (24).convertDpToPx(parent.context)
        val right = parent.width - parent.paddingRight - (24).convertDpToPx(parent.context)
        val childCount = parent.childCount


        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight

            params.bottomMargin = spacingDP
            params.rightMargin = spacingDP
            params.leftMargin = spacingDP
            params.topMargin = spacingDP

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }

    }

    /*
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (view.width == DisplayMetrics().widthPixels) {
            val spacingMiddle = spacingDP / 2
            val currentPosition = parent.getChildAdapterPosition(view)
                .let { if (it == RecyclerView.NO_POSITION) return else it }
            when (currentPosition) {
                0 -> {
                    outRect.top = spacingDP
                    outRect.bottom = spacingMiddle
                }
                state.itemCount - 1 -> {
                    outRect.top = spacingMiddle
                    outRect.bottom = spacingDP
                }
                else -> {
                    outRect.top = spacingMiddle
                    outRect.bottom = spacingMiddle
                }
            }

            outRect.left = spacingDP
            outRect.right = spacingDP
        }
    }

     */

}
