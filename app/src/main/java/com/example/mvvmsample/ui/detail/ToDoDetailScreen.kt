package com.example.mvvmsample.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmsample.model.todo.ToDo

@Composable
fun ToDoDetailScreen(
    navController: NavController,
    viewModel: ToDoDetailViewModel,
) {
    val scaffoldState = rememberScaffoldState()
    val todo = viewModel.todo.collectAsState(initial = emptyToDo)
    val showDialog = remember { mutableStateOf(false) }

    val errorMessage = viewModel.errorMessage.collectAsState()
    val deleted = viewModel.deleted.collectAsState()

    if (errorMessage.value.isNotEmpty()) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage.value
            )
            viewModel.errorMessage.value = ""
        }
    }

    if (deleted.value) {
        // 再コンポーズ時にもう一度実行されたら困る
        viewModel.deleted.value = false
        navController.popBackStack()
    }

    Scaffold(
        topBar = {

            DetailTopBar(navController, todo.value, {
                navController.navigate("edit/${todo.value._id}")
            }) { // deleteClickedの部分
                showDialog.value = true
            }
        },
    ) {
        DetailBody(todo = todo.value, showDialog) {
            viewModel.delete(todo.value)
        }
    }
}

@Composable
fun DetailTopBar(
    navController: NavController,
    todo: ToDo,
    toEdit: () -> Unit,
    deleteClicked: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

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
                // 三展アイコン
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Filled.MoreVert, "Menu")
                }
                // ポップアップメニュー部分
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        showMenu = false
                        deleteClicked()
                    }) {
                        Text(text = "Delete TO DO")
                    }
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
fun DetailBody(
    todo: ToDo,
    showDialog: MutableState<Boolean>,
    performDelete: () -> Unit
) {
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

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "Delete Message")
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    performDelete()
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}