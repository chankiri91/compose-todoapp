package com.example.mvvmsample.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmsample.model.todo.ToDo

@Composable
fun ToDoDetailScreen(
    navController: NavController,
    viewModel: ToDoDetailViewModel,
) {
    val todo = viewModel.todo.collectAsState(initial = emptyToDo)

    Scaffold(
        topBar = {
            DetailTopBar(navController, todo.value) {
                navController.navigate("edit/${todo.value._id}")
            }
        },
    ) {
        DetailBody(todo = todo.value)
    }
}

@Composable
fun DetailTopBar(
    navController: NavController,
    todo: ToDo,
    toEdit: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, "back")
            }
        },
        title = {
            if (todo._id == emptyToDoId) {
                Text(text = "loading")
            } else {
                Text(text = todo.title)
            }
        },
        actions = {
            if (todo._id != emptyToDoId) {
                IconButton(onClick = toEdit) {
                    Icon(Icons.Filled.Edit, "Edit")
                }
            }
        }
    )
}

private const val emptyToDoId = -1
private val emptyToDo = ToDo(
    _id = emptyToDoId,
    title = "",
    detail = "",
    created = 0,
    modified = 0
)

@Composable
fun DetailBody(todo: ToDo) {
    Column {
        Text(
            text = todo.title,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
        Text(
            text = todo.detail,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1.0f, true)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}