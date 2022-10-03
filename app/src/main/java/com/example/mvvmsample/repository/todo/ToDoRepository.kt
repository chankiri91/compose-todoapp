package com.example.mvvmsample.repository.todo

import com.example.mvvmsample.model.todo.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun getAll(): Flow<List<ToDo>>
    fun getById(todoId: Int): Flow<ToDo>
    suspend fun update(todo: ToDo, title: String, detail: String)
    suspend fun create(title: String, detail: String) : ToDo
    suspend fun delete(todo: ToDo)
}