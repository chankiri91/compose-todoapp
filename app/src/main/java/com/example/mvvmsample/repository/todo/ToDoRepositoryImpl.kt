package com.example.mvvmsample.repository.todo

import com.example.mvvmsample.model.todo.ToDo
import com.example.mvvmsample.model.todo.ToDoDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ToDoRepositoryImpl @Inject constructor(
    private val dao: ToDoDAO
) : ToDoRepository {
    override fun getAll(): Flow<List<ToDo>> {
        return dao.getAll()
    }

    override fun getById(todoId: Int): Flow<ToDo> {
        return dao.getById(todoId).take(1).map { list -> list[0] }
    }

    override suspend fun update(todo: ToDo, title: String, detail: String) {
        val updateToDo = ToDo(
            _id = todo._id,
            title = title,
            detail = detail,
            created = todo.created,
            modified = System.currentTimeMillis()
        )
        withContext(Dispatchers.IO) {
            dao.update(updateToDo)
        }
    }

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
