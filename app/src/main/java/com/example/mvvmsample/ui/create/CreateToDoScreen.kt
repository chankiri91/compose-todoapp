package com.example.mvvmsample.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
// navController, viewModelを受け取る
fun CreateToDoScreen(
    navController: NavController,
    viewModel: CreateToDoViewModel
) {
    // ScaffoldStateを追加
    val scaffoldState = rememberScaffoldState()
    // 状態
    val title = rememberSaveable { mutableStateOf("") }
    val detail = rememberSaveable { mutableStateOf("") }

    // Flowは状態として扱える
    val errorMessage = viewModel.errorMessage.collectAsState()
    val done = viewModel.done.collectAsState()

    if (errorMessage.value.isNotEmpty()) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage.value
            )
            // 画面回転すると再度表示されちゃうのを抑制
            viewModel.errorMessage.value = ""
        }
    }

    if (done.value) {
        // 再コンポーズ時にもう一度実行されたら困る
        viewModel.done.value = false
        navController.popBackStack()
        // returnすると一瞬真っ白になっちゃう
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CreateTopBar(navController) {
                // 実際の処理はViewModelにやらせる
                viewModel.save(title.value, detail.value)
            }
        }
    ) {
        // 引数として渡す
        CreateToDoBody(title, detail)
    }
}

@Composable
fun CreateTopBar(navController: NavController, save: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                // 1つ前の画面に戻る
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        },
        title = {
            Text(text = "Create TO DO")
        },
        // アクションとして追加
        actions = {
            // タップされたときの処理は親で決める
            IconButton(onClick = save) {
                Icon(Icons.Filled.Done, "save")
            }
        }
    )
}

@Composable
fun CreateToDoBody(
    title: MutableState<String>,
    detail: MutableState<String>
) {
    Column {
        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text(text = "Title")},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        TextField(
            value = detail.value,
            onValueChange = { detail.value = it },
            label = { Text(text = "Detail") },
            modifier = Modifier
                .weight(1.0f, true)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}