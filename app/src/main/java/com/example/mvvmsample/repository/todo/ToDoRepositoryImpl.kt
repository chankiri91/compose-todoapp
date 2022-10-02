package com.example.mvvmsample.repository.todo

import com.example.mvvmsample.model.todo.ToDo
import com.example.mvvmsample.model.todo.ToDoDAO
import javax.inject.Inject


class ToDoRepositoryImpl @Inject constructor(
    private val dao: ToDoDAO
): ToDoRepository {
    override suspend fun create(title: String, detail: String): ToDo {
        val todo = ToDo(
            title = title,
            detail = detail,
            created = System.currentTimeMillis(),
            modified = System.currentTimeMillis()
        )
        dao.create(todo)
        return todo
    }
}