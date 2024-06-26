package com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment.recycler

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem

class TodoViewHolder(itemView: View, onItemClicked: (Int) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val todoText = itemView.findViewById<TextView>(R.id.todoTextView)
    private val todoCheckBox = itemView.findViewById<CheckBox>(R.id.todoCheckBox)

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun onBind(todoItem: TodoItem) {
        itemView.apply {
            todoText.text =
                when (todoItem.importance) {
                    Importance.URGENT -> "!! ${todoItem.text}"
                    Importance.NORMAL -> todoItem.text
                    Importance.LOW -> "↓ ${todoItem.text}"
                }
            todoCheckBox.isChecked = todoItem.isDone
            setDecorations(todoItem)
            todoCheckBox.setOnCheckedChangeListener { _, isChecked ->
                todoItem.isDone = isChecked
                setDecorations(todoItem)
            }
        }
    }

    private fun setDecorations(todoItem: TodoItem) {
        with(todoText) {
            setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (todoItem.isDone) R.color.light_gray else R.color.black
                )
            )
            paintFlags = if (todoItem.isDone) Paint.STRIKE_THRU_TEXT_FLAG else Paint.ANTI_ALIAS_FLAG
        }
    }
}