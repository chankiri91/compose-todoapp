package com.example.mvvmsample.repository.todo

import com.example.mvvmsample.model.todo.ToDo

interface ToDoRepository {
    suspend fun create(title: String, detail: String) : ToDo
}