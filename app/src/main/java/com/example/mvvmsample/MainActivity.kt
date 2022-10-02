package com.example.mvvmsample

import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmsample.ui.create.CreateToDoScreen
import com.example.mvvmsample.ui.create.CreateToDoViewModel
import com.example.mvvmsample.ui.detail.ToDoDetailScreen
import com.example.mvvmsample.ui.detail.ToDoDetailViewModel
import com.example.mvvmsample.ui.edit.EditToDoScreen
import com.example.mvvmsample.ui.edit.EditToDoViewModel
import com.example.mvvmsample.ui.main.MainScreen
import com.example.mvvmsample.ui.main.MainViewModel
import com.example.mvvmsample.ui.theme.MVVMSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ToDoApp()
                }
            }
        }
    }
}

@Composable
fun ToDoApp() {
    // navControllerの取得
    val navController = rememberNavController()

    // 画面遷移を担当してもらうNavHost
    // 引数にはNavControllerとstartDestination
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            // hiltViewModel()を使ってviewModelインスタンスを各スクリーンに渡す
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, viewModel = viewModel)
        }
        // 作成画面
        composable("create") {
            val viewModel = hiltViewModel<CreateToDoViewModel>()
            CreateToDoScreen(navController = navController, viewModel = viewModel)
        }
        // 詳細画面
        composable(
            "detail/{todoId}",
            // NavHostのルートの引数は、デフォルトで文字列としてパースされるため、
            //　数値のみを想定してる場合、次のようにargumentsを指定する
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<ToDoDetailViewModel>()
            // ルートの引数(todoId)はbackStackEntry.arguments?.getString("todoId")で取得できる
            val todoId = backStackEntry.arguments?.getString("todoId")?.toInt() ?: 0
            ToDoDetailScreen(navController = navController, viewModel = viewModel, todoId = todoId)
        }
        // 編集画面
        composable(
            "edit/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<EditToDoViewModel>()
            val todoId = backStackEntry.arguments?.getString("todoId")?.toInt() ?: 0
            EditToDoScreen(navController = navController, viewModel = viewModel, todoId = todoId)
        }
    }
}
