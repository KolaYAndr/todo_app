package com.cleverpumpkin.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todoapp.presentation.navigation.Navigation
import com.cleverpumpkin.todoapp.presentation.util.getThemeFlagByString
import com.cleverpumpkin.todoapp.presentation.view_model.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel = hiltViewModel<MainActivityViewModel>()
            val isDark = getThemeFlagByString(string = mainViewModel.getThemeString())
            TodoAppTheme(
                darkTheme = isDark
            ) {
                val navHostController = rememberNavController()
                Navigation(navController = navHostController, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
