package com.cleverpumpkin.todoapp.presentation.fragments.todo_detail_fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.databinding.FragmentTodoDetailBinding
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.presentation.navigation.NavArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TodoDetailFragment : Fragment() {
    private var _binding: FragmentTodoDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by viewModels<TodoDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoId = requireArguments().getString(NavArgs.TODO_ID)!!
        if (todoId != NavArgs.CREATE_TODO) {
            detailViewModel.findItem(todoId)
            collectUiState()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            detailViewModel.uiState.collectLatest { state ->
                when (state) {
                    is TodoDetailState.Error -> showErrorMessage(state.message)
                    is TodoDetailState.Item -> bindTodoInfo(composeItem(state))
                    TodoDetailState.Loading -> showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.detailViewFlipper.displayedChild = 2
    }

    private fun showErrorMessage(message: String?) {
        binding.detailErrorTextView.text = message ?: getString(R.string.unexpected_error)
        binding.detailViewFlipper.displayedChild = 1
    }

    private fun bindTodoInfo(todoItem: TodoItem) {
        binding.detailViewFlipper.displayedChild = 0
        with(binding.detailDeleteButton) {
            isEnabled = true
            setOnClickListener { detailViewModel.deleteItem(composeItem()) }
            findNavController().navigateUp()
        }
        binding.detailSaveButton.setOnClickListener {
            detailViewModel.addItem(composeItem())
            findNavController().navigateUp()
        }
        binding.todoDetailTopAppBar.setOnClickListener {
            findNavController().navigateUp()
        }
        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        with(binding.deadlineSwitch) {
            isChecked = todoItem.deadline != null
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    with(binding.actualDeadlineTextView) {
                        text = formatter.format(LocalDateTime.now())
                        visibility = View.VISIBLE
                    }
                }
            }
        }
        binding.taskTextView.setText(todoItem.text)
        with(binding.actualDeadlineTextView) {
            if (todoItem.deadline != null) {
                text = formatter.format(todoItem.deadline)
                visibility = View.VISIBLE
                setOnClickListener {
                    openDatePickerDialog()
                }
            } else visibility = View.GONE
        }
        binding.importanceTextView.text = when (todoItem.importance) {
            Importance.LOW -> ContextCompat.getString(requireContext(), R.string.importance_no)
            Importance.NORMAL -> ContextCompat.getString(
                requireContext(),
                R.string.importance_normal
            )

            Importance.URGENT -> ContextCompat.getString(
                requireContext(),
                R.string.importance_urgent
            )
        }
    }

    private fun openDatePickerDialog() {
        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        DatePickerDialog(requireContext()).setOnDateSetListener { view, year, month, dayOfMonth ->
            binding.actualDeadlineTextView.text =
                formatter.format(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0))
        }
    }

    private fun composeItem(): TodoItem {
        return TodoItem(
            id = "100",
            text = binding.taskTextView.text.toString(),
            importance = when (binding.actualImportanceTextView.text) {
                ContextCompat.getString(
                    requireContext(),
                    R.string.importance_normal
                ) -> Importance.NORMAL

                ContextCompat.getString(
                    requireContext(),
                    R.string.importance_urgent
                ) -> Importance.URGENT

                else -> Importance.LOW
            },
            createdAt = LocalDateTime.now(),
            modifiedAt = null,
            isDone = false,
            deadline = if (binding.deadlineSwitch.isChecked) LocalDateTime.parse(binding.actualDeadlineTextView.text) else null
        )
    }

    private fun composeItem(state: TodoDetailState.Item): TodoItem {
        return TodoItem(
            id = state.id,
            text = state.text,
            importance = state.importance,
            createdAt = state.createdAt,
            modifiedAt = LocalDateTime.now(),
            isDone = state.isDone,
            deadline = state.deadline
        )
    }
}