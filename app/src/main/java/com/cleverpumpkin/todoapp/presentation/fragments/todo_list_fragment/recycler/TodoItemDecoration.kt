package com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cleverpumpkin.todoapp.presentation.utils.toPx

class TodoItemDecoration(
    private val leftOffset: Int = 0,
    private val topOffset: Int = 0,
    private val rightOffset: Int = 0,
    private val bottomOffset: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(
            leftOffset.toPx(),
            topOffset.toPx(),
            rightOffset.toPx(),
            bottomOffset.toPx()
        )
    }
}