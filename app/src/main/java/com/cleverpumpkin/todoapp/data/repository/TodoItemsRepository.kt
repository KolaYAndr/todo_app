package com.cleverpumpkin.todoapp.data.repository

import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class TodoItemsRepository : TodoItemsRepo {
    private val itemsList = MutableStateFlow(
        mutableListOf(
            TodoItem(
                id = "1",
                text = "Купить продукты",
                importance = Importance.Normal,
                deadline = LocalDateTime.of(2024, 6, 20, 12, 0),
                isDone = false,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "2",
                text = "Поздравить брата",
                importance = Importance.Urgent,
                deadline = LocalDateTime.of(2024, 6, 16, 12, 0),
                isDone = false,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "0",
                text = "Проснуться, улыбнуться",
                importance = Importance.Low,
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "3",
                text = "Зарядка утром",
                importance = Importance.Low,
                isDone = false,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "4",
                text = "Погладить кота",
                importance = Importance.Low,
                isDone = false,
                createdAt = LocalDateTime.now(),
                deadline = LocalDateTime.of(2024, 6, 16, 12, 0)
            ),
            TodoItem(
                id = "5",
                text = "Сходить к Пятачку",
                importance = Importance.Low,
                isDone = false,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "6",
                text = "Заботать домашку",
                importance = Importance.Normal,
                isDone = false,
                createdAt = LocalDateTime.now(),
                deadline = LocalDateTime.of(2024, 6, 16, 12, 0),
                modifiedAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "7",
                text = "Создать гит для домашек",
                importance = Importance.Urgent,
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "8",
                text = "Вынести мусор",
                importance = Importance.Low,
                isDone = false,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "9",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Low,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "10",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Low,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "11",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Low,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "12",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Low,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "13",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Low,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "14",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Low,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "15",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Urgent,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "16",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Urgent,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "17",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Urgent,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "18",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Low,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "19",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Urgent,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            ),
            TodoItem(
                id = "20",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
                importance = Importance.Urgent,
                deadline = LocalDateTime.now(),
                isDone = true,
                createdAt = LocalDateTime.now()
            )
        )
    )

    override suspend fun fetchTodoItems(): StateFlow<List<TodoItem>> = itemsList.asStateFlow()

    override suspend fun addTodoItem(item: TodoItem) = withContext(Dispatchers.IO) {
        itemsList.update {
            (listOf(item) + it).toSet().toMutableList()
        }
    }

    override suspend fun getCompletedNumber(): Int = withContext(Dispatchers.IO) {
        itemsList.value.count { it.isDone }
    }


    override suspend fun deleteTodoItem(item: TodoItem) = withContext(Dispatchers.IO) {
        itemsList.update {
            (it - listOf(item).toSet()).toMutableList()
        }
    }

    override suspend fun findItemById(id: String): TodoItem =
        withContext(Dispatchers.IO) { itemsList.value.first { it.id == id } }
}

