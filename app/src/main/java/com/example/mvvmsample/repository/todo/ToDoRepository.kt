package com.example.mvvmsample.repository.todo

import com.example.mvvmsample.model.todo.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun create(title: String, detail: String) : ToDo
    fun getAll(): Flow<List<ToDo>>
    fun getById(todoId: Int): Flow<ToDo>
}