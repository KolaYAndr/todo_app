package com.cleverpumpkin.todoapp.presentation.navigation

import android.view.ContextThemeWrapper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.cleverpumpkin.about.presentation.AboutScreen
import com.cleverpumpkin.about.presentation.AboutScreenViewModel
import com.cleverpumpkin.auth.auth_screen.AuthScreen
import com.cleverpumpkin.auth.auth_screen.AuthViewModel
import com.cleverpumpkin.core.presentation.navigation.NavArgs
import com.cleverpumpkin.core.presentation.navigation.NavRoutes
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.settings.presentation.SettingsScreen
import com.cleverpumpkin.settings.presentation.SettingsScreenViewModel
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.TodoDetailScreen
import com.cleverpumpkin.todo.presentation.screens.todo_list_screen.TodoListScreen
import com.cleverpumpkin.todo.presentation.screens.todo_list_screen.TodoListViewModel
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.presentation.util.getThemeFlagByString
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoutes.AUTH_SCREEN,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(500)
            )
        }

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
                onEndToStartAction = { item -> todoListViewModel.deleteItem(item) },
                onAddItem = { navController.navigate("${NavRoutes.TODO_DETAIL_SCREEN}/${NavArgs.CREATE_TODO}") },
                onNavigate = { id -> navController.navigate("${NavRoutes.TODO_DETAIL_SCREEN}/${id}") },
                onNavigateToSettings = { navController.navigate(NavRoutes.SETTINGS_SCREEN) },
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
            val todoDetailViewModel =
                hiltViewModel<com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.TodoDetailViewModel>()
            val state = todoDetailViewModel.uiState.collectAsStateWithLifecycle()
            val id = it.arguments?.getString(NavArgs.TODO_ID) ?: NavArgs.CREATE_TODO
            todoDetailViewModel.findItem(id)
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

        composable(route = NavRoutes.SETTINGS_SCREEN) {
            val settingsScreenViewModel = hiltViewModel<SettingsScreenViewModel>()
            val themeString by remember { mutableStateOf(settingsScreenViewModel.getThemeString()) }
            SettingsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TodoAppTheme.colorScheme.backPrimary),
                onNavigation = { navController.navigate(NavRoutes.ABOUT_SCREEN) },
                onChangeTheme = { settingsScreenViewModel.setTheme(it)},
                themeString = themeString
            )
        }

        composable(route = NavRoutes.ABOUT_SCREEN) {
            val aboutViewModel = hiltViewModel<AboutScreenViewModel>()
            val darkTheme = getThemeFlagByString(string = aboutViewModel.getThemeString())
            val themeString = remember { mutableStateOf(if (darkTheme) "dark" else "light") }
            val context = LocalContext.current
            val contextWrapper = remember { ContextThemeWrapper(context, R.style.Theme_TodoApp) }
            AboutScreen(
                contextThemeWrapper = contextWrapper,
                onNavigation = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxSize()
                    .background(TodoAppTheme.colorScheme.backPrimary),
                themeString = themeString.value
            )
        }
    }
}
