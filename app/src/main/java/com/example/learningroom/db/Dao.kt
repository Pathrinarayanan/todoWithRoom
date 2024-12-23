package com.example.learningroom.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.learningroom.Todo
import java.util.Date

@Dao
 interface MyDao {
    @Query("SELECT * FROM todo")
     fun getTodo() : LiveData<List<Todo>>

     @Insert
     suspend fun addTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE ID=:id ")
      fun deleteTodo( id : Int)
}