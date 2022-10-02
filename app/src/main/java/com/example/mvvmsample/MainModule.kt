package com.example.mvvmsample

import android.content.Context
import androidx.room.Room
import com.example.mvvmsample.model.todo.ToDoDAO
import com.example.mvvmsample.model.todo.ToDoDatabase
import com.example.mvvmsample.repository.todo.ToDoRepository
import com.example.mvvmsample.repository.todo.ToDoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Hilt Moduleよくわからない
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindToDoRepository(impl: ToDoRepositoryImpl): ToDoRepository
}

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    // ToDoDatabaseを返す
    fun provideToDoDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            // アプリコンテキスト
            context,
            // データベースクラス
            ToDoDatabase::class.java,
            // データベースを作るファイル名
            "todo.db"
        ).build()
    }

    @Provides
    @Singleton
    // ToDoDAOを返す
    fun provideToDoDAO(db: ToDoDatabase): ToDoDAO {
        return db.todoDAO()
    }

}