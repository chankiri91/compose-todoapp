package com.example.mvvmsample.ui.main

import androidx.lifecycle.ViewModel
import com.example.mvvmsample.repository.todo.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ToDoRepository
): ViewModel() {
    val todoList = repo.getAll()
}