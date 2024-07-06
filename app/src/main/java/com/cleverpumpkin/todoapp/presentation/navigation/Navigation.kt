package com.cleverpumpkin.todoapp.presentation.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cleverpumpkin.todoapp.presentation.auth_screen.AuthScreen
import com.cleverpumpkin.todoapp.presentation.auth_screen.AuthViewModel
import com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen.TodoDetailScreen
import com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen.TodoDetailViewModel
import com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen.TodoListScreen
import com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen.TodoListViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.AUTH_SCREEN
    ) {
        composable(route = NavRoutes.AUTH_SCREEN) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            val state = authViewModel.uiState.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val contract = remember {
                YandexAuthSdk.create(YandexAuthOptions(context)).contract
            }
            val launcher = rememberLauncherForActivityResult(contract = contract) { result ->
                authViewModel.yandexAuth(result)
            }
            AuthScreen(
                state = state,
                onYandexAuth = { launcher.launch(YandexAuthLoginOptions()) },
                onBasicAuth = { authViewModel.apiKeyAuth() },
                onNavigateForward = { navController.navigate(NavRoutes.TODO_LIST_SCREEN) })
        }

        composable(route = NavRoutes.TODO_LIST_SCREEN) {
            val todoListViewModel = hiltViewModel<TodoListViewModel>()
            val state = todoListViewModel.uiState.collectAsStateWithLifecycle()
            TodoListScreen(
                state = state,
                onEndToStartAction = { item -> todoListViewModel.deleteItem(item.id) },
                onAddItem = { navController.navigate("${NavRoutes.TODO_DETAIL_SCREEN}/${NavArgs.CREATE_TODO}") },
                onNavigate = { id -> navController.navigate("${NavRoutes.TODO_DETAIL_SCREEN}/${id}") },
                onFilter = { todoListViewModel.filter() },
                onCheck = { item -> todoListViewModel.checkItem(item) },
                onRefresh = { todoListViewModel.refresh() },
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
        ) {
            val todoDetailViewModel = hiltViewModel<TodoDetailViewModel>()
            val state = todoDetailViewModel.uiState.collectAsStateWithLifecycle()
            TodoDetailScreen(
                state = state,
                onSave = {
                    todoDetailViewModel.saveItem()
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
                onRefresh = { todoDetailViewModel.refresh() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
