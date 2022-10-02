package com.example.mvvmsample.model.todo

import androidx.room.Database
import androidx.room.RoomDatabase

// 1つのデータベースには複数のテーブルが入ってるため、どのテーブルが入っているかを記述する必要がある。
@Database(entities = [ToDo::class], version = 1)
// RoomDatabase()を継承したクラスをつくる。
abstract class ToDoDatabase: RoomDatabase() {
    // 抽象メソッドとして、DAOを返すメソッドを定義する
    abstract fun todoDAO(): ToDoDAO
}