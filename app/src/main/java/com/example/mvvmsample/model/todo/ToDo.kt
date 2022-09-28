package com.example.mvvmsample.model.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entityおつけると、Roomは対応するテーブルを自動で作ってあげる
@Entity
data class ToDo(
    // @PrimaryKeyをつけるとその列が主キーになる
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val title: String,
    val detail: String,
    val created: Long,
    val modified: Long
)