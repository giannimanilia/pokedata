package com.gmaniliapp.pokedata.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * This class handles the right way of setting the margins of horizontal lists
 */
class HorizontalItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != 0) {
                left =  spaceHeight
            }
            top = spaceHeight
            bottom = spaceHeight
        }
    }
}