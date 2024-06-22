package com.cleverpumpkin.todoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.cleverpumpkin.todoapp.databinding.ActivityMainBinding
import com.cleverpumpkin.todoapp.presentation.fragments.todo_detail_fragment.TodoDetailFragment
import com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment.TodoListFragment
import com.cleverpumpkin.todoapp.presentation.navigation.NavArgs
import com.cleverpumpkin.todoapp.presentation.navigation.NavRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setNavigation()
    }

    private fun setNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        val navController = navHost.navController
        navController.graph = navController.createGraph(NavRoutes.TODO_LIST_SCREEN) {
            fragment<TodoListFragment>(NavRoutes.TODO_LIST_SCREEN)
            fragment<TodoDetailFragment>("${NavRoutes.TODO_DETAIL_SCREEN}/{${NavArgs.TODO_ID}}") {
                argument(NavArgs.TODO_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            }
        }
    }
}