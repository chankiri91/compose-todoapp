package com.example.mvvmsample.ui.main

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmsample.model.todo.ToDo

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    // StateなのでFlowが最初のデータを流してくるまでの間、初期値が必要
    val todoList = viewModel.todoList.collectAsState(emptyList())

    Scaffold(
        topBar = { MainTopBar() },
        floatingActionButton = { MainFAB(navController) }
    ) {
        ToDoList(todoList) { todo ->
            Log.v("detail", "detail")
            // IDをパスパラメータとしてセット
            navController.navigate("detail/${todo._id}")
            Log.v("detail2", "detail2")
        }
    }
}

@Composable
fun MainTopBar() {
    TopAppBar(
        title = {
            Text(text = ("TO DO APP"))
        }
    )
}

@Composable
fun MainFAB(navController: NavController) {
    FloatingActionButton(onClick = {
        // 作成画面へ画面遷移
        navController.navigate("create")
    }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun ToDoList(list: State<List<ToDo>>, itemSelected: (todo: ToDo) -> Unit) {
    LazyColumn {
        items(list.value) { todo ->
            ToDoListItem(todo, itemSelected)
        }
    }
}

@Composable
fun ToDoListItem(todo: ToDo, itemSelected: (todo: ToDo) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable { itemSelected(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = todo.title,
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = DateFormat.format("yyyy-MM-dd hh:mm:ss", todo.created).toString(),
            style = MaterialTheme.typography.body2
        )
    }
}
