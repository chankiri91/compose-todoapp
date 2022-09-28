package com.example.mvvmsample

import android.content.Context
import androidx.room.Room
import com.example.mvvmsample.model.todo.ToDoDAO
import com.example.mvvmsample.model.todo.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideToDoDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "todo.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoDAO(db: ToDoDatabase): ToDoDAO {
        return db.todoDAO()
    }
}