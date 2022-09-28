package com.example.mvvmsample.ui.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Scaffold(
        topBar = { MainTopBar() },
        floatingActionButton = { MainFAB(navController) }
    ) {

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