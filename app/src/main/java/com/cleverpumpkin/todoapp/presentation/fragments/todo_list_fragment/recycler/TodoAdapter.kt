package com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.domain.models.TodoItem

class TodoAdapter(private val onItemClick: (TodoItem) -> Unit): RecyclerView.Adapter<TodoViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, callback)

    fun submitList(hotels: List<TodoItem>) {
        differ.submitList(hotels)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item_view, parent, false)
        return TodoViewHolder(view) { index ->
            onItemClick(differ.currentList[index])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = differ.currentList[position]
        holder.onBind(todoItem)
    }
}