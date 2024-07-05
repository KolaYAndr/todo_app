package com.cleverpumpkin.todoapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen.TodoDetailScreen
import com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen.TodoDetailViewModel
import com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen.TodoListScreen
import com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen.TodoListViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.TODO_LIST_SCREEN
    ) {
        composable(route = NavRoutes.TODO_LIST_SCREEN) {
            val todoListViewModel = hiltViewModel<TodoListViewModel>()
            val state = todoListViewModel.uiState.collectAsStateWithLifecycle()
            TodoListScreen(
                state = state,
                onEndToStartAction = { item -> todoListViewModel.deleteItem(item) },
                onAddItem = { navController.navigate("${NavRoutes.TODO_DETAIL_SCREEN}/${NavArgs.CREATE_TODO}") },
                onNavigate = { id -> navController.navigate("${NavRoutes.TODO_DETAIL_SCREEN}/${id}") },
                onFilter = { todoListViewModel.filter() },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(
            route = "${NavRoutes.TODO_DETAIL_SCREEN}/{${NavArgs.TODO_ID}}",
            arguments = listOf(
                navArgument(NavArgs.TODO_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val todoDetailViewModel = hiltViewModel<TodoDetailViewModel>()
            val id = backStackEntry.arguments?.getString(NavArgs.TODO_ID) ?: NavArgs.CREATE_TODO
            todoDetailViewModel.findItem(id)
            val state = todoDetailViewModel.uiState.collectAsStateWithLifecycle()
            TodoDetailScreen(
                state = state,
                onSave = {
                    todoDetailViewModel.addItem()
                    navController.navigateUp()
                },
                onNavBack = { navController.navigateUp() },
                onSelectImportance = { todoDetailViewModel.selectImportance(it) },
                onDelete = {
                    todoDetailViewModel.deleteItem()
                    navController.navigateUp()
                },
                onTextChange = { todoDetailViewModel.changeText(it) },
                onSelectDeadline = { todoDetailViewModel.selectDeadline(it) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}