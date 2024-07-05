package com.cleverpumpkin.todoapp.data.remote.utils

object HttpRoutes {
    private const val BASE_URL = "https://hive.mrdekk.ru/todo"

    fun getItemsListRoute() = "$BASE_URL/list"

    fun updateItemListRoute() = "$BASE_URL/list"

    fun getItemByIdRoute(id: String) = "$BASE_URL/list/$id"

    fun addItemRoute() = "$BASE_URL/list"

    fun changeItemRoute(id: String) = "$BASE_URL/list/$id"

    fun deleteItemByIdRoute(id: String) = "$BASE_URL/list/$id"
}