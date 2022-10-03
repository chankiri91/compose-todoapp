package com.example.mvvmsample.ui.detail

import androidx.lifecycle.ViewModel
import com.example.mvvmsample.model.todo.ToDo
import com.example.mvvmsample.repository.todo.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ToDoDetailViewModel @Inject constructor(
    private val repo:ToDoRepository
): ViewModel() {
    private val todoId = MutableStateFlow(-1)

    @ExperimentalCoroutinesApi
    val todo: Flow<ToDo> = todoId.flatMapLatest { todoId -> repo.getById(todoId) }

    fun setId(todoId: Int) {
        this.todoId.value = todoId
    }
}