package com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.databinding.FragmentTodoListBinding
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment.recycler.TodoAdapter
import com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment.recycler.TodoItemDecoration
import com.cleverpumpkin.todoapp.presentation.navigation.NavArgs
import com.cleverpumpkin.todoapp.presentation.navigation.NavRoutes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var todoAdapter: TodoAdapter

    private val listViewModel by viewModels<TodoListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        collectUiState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initAdapter() {
        todoAdapter = TodoAdapter { todoItem ->
            navigateToDetailFragmentEdit(todoItem.id)
        }
        with(binding.todoListRecycler) {
            adapter = todoAdapter
            addItemDecoration(
                TodoItemDecoration(
                    leftOffset = 16,
                    topOffset = 12,
                    rightOffset = 16,
                    bottomOffset = 12
                )
            )
        }
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            listViewModel.uiState.collectLatest { state ->
                when (state) {
                    is TodoListState.Error -> showErrorTextView(state.message)
                    is TodoListState.Todos -> showTodos(state.items)
                    is TodoListState.Loading -> showProgressBar()
                }
            }
        }
    }

    private fun navigateToDetailFragmentEdit(itemId: String) {
        findNavController().navigate("${NavRoutes.TODO_DETAIL_SCREEN}/$itemId")
    }

    private fun navigateToDetailFragmentCreate() {
        findNavController().navigate("${NavRoutes.TODO_DETAIL_SCREEN}/${NavArgs.CREATE_TODO}")
    }

    private fun showTodos(hotels: List<TodoItem>) {
        todoAdapter.submitList(hotels)
        binding.todoListViewFlipper.displayedChild = 0
        binding.addTodoFab.setOnClickListener {
            navigateToDetailFragmentCreate()
        }
    }

    private fun showProgressBar() {
        binding.todoListViewFlipper.displayedChild = 2
    }

    private fun showErrorTextView(message: String?) {
        binding.todoListErrorTextView.text = message ?: getString(R.string.unknown_error)
        binding.todoListViewFlipper.displayedChild = 1
    }
}