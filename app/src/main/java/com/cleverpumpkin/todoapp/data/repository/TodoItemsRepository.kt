package com.cleverpumpkin.todoapp.data.repository

import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepo
import java.time.LocalDateTime

class TodoItemsRepository : TodoItemsRepo {
    private val itemsList = mutableListOf(
        TodoItem(
            id = "1",
            text = "Купить продукты",
            importance = Importance.NORMAL,
            deadline = LocalDateTime.of(2024, 6, 20, 12, 0),
            isDone = false,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "2",
            text = "Поздравить брата",
            importance = Importance.URGENT,
            deadline = LocalDateTime.of(2024, 6, 16, 12, 0),
            isDone = false,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "0",
            text = "Проснуться, улыбнуться",
            importance = Importance.LOW,
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "3",
            text = "Зарядка утром",
            importance = Importance.LOW,
            isDone = false,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "4",
            text = "Погладить кота",
            importance = Importance.LOW,
            isDone = false,
            createdAt = LocalDateTime.now(),
            deadline = LocalDateTime.of(2024, 6, 16, 12, 0)
        ),
        TodoItem(
            id = "5",
            text = "Сходить к Пятачку",
            importance = Importance.LOW,
            isDone = false,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "6",
            text = "Заботать домашку",
            importance = Importance.NORMAL,
            isDone = false,
            createdAt = LocalDateTime.now(),
            deadline = LocalDateTime.of(2024, 6, 16, 12, 0),
            modifiedAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "7",
            text = "Создать гит для домашек",
            importance = Importance.URGENT,
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "8",
            text = "Вынести мусор",
            importance = Importance.LOW,
            isDone = false,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "9",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            importance = Importance.LOW,
            deadline = LocalDateTime.now(),
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "10",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            importance = Importance.LOW,
            deadline = LocalDateTime.now(),
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "11",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            importance = Importance.LOW,
            deadline = LocalDateTime.now(),
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "12",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            importance = Importance.LOW,
            deadline = LocalDateTime.now(),
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "13",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            importance = Importance.LOW,
            deadline = LocalDateTime.now(),
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "14",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            importance = Importance.LOW,
            deadline = LocalDateTime.now(),
            isDone = true,
            createdAt = LocalDateTime.now()
        ),
        TodoItem(
            id = "15",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            importance = Importance.URGENT,
            deadline = LocalDateTime.now(),
            isDone = true,
            createdAt = LocalDateTime.now()
        )
    )

    override fun getAllItems(): List<TodoItem> {
        return itemsList
    }

    override fun addTodoItem(item: TodoItem) = itemsList.add(item)

    override fun deleteTodoItem(item: TodoItem) = itemsList.remove(item)

    override fun findItemById(id: String): TodoItem = itemsList.first { it.id == id }
}
