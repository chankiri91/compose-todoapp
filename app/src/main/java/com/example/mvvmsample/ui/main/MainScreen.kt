package com.example.mvvmsample.ui.main

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmsample.R
import com.example.mvvmsample.model.todo.ToDo
import com.example.mvvmsample.ui.theme.MVVMSampleTheme

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
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo), null,
                modifier = Modifier.clip(RoundedCornerShape(50))
                )
        },
        title = {
            Text(text = ("TO DO APP"), style = MaterialTheme.typography.h1)
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
    LazyColumn(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        items(list.value) { todo ->
            ToDoListItem(todo, itemSelected)
        }
    }
}

@Composable
fun ToDoListItem(todo: ToDo, itemSelected: (todo: ToDo) -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .clickable { itemSelected(todo) }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.h2,
            )
            Text(
                text = DateFormat.format("yyyy-MM-dd hh:mm:ss", todo.created).toString(),
                style = MaterialTheme.typography.body1,
            )
        }
    }
}

@Preview
@Composable
fun PreviewToDoListItem() {
    MVVMSampleTheme() {
        Surface() {
            val todo: ToDo = ToDo(1, "title", "detail", 111, 222)
            ToDoListItem(todo = todo, itemSelected = {})
        }
    }
}

@Preview
@Composable
fun PreviewToDoList() {
    MVVMSampleTheme() {
        Surface() {
            val todo: ToDo = ToDo(1, "title", "detail", 111, 222)
            ToDoList(list = mutableStateOf(listOf<ToDo>(todo, todo, todo)), itemSelected = {})
        }
    }
}