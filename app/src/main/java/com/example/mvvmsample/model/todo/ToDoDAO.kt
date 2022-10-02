package com.example.mvvmsample.model.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Data Access Object, interfaceで定義
@Dao
interface ToDoDAO {
    // 取得
    // 作成日時が指定したもの未満で、上位n件をとってくる
    // :で始まるパラメータは、メソッドの引数で渡された値に置き換えられて実行される。
    @Query("select * from ToDo where created < :startCreated order by created desc limit :limit")
    // 戻り値の型をKotlinコルーチンのFlow<T>にすることで、データベースの監視機能がつく
    fun getWithCreated(startCreated: Long, limit: Int): Flow<List<ToDo>>

    @Query("select * from ToDo order by created desc")
    fun getAll(): Flow<List<ToDo>>

    // 追加
    @Insert
    suspend fun create(todo: ToDo)

    // 更新
    @Update
    suspend fun update(todo: ToDo)

    // 削除
    @Delete
    suspend fun delete(todo:ToDo)
}