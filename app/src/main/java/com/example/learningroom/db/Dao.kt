package com.example.learningroom.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.learningroom.Todo
import java.time.LocalDate
import java.util.Date

@Dao
 interface MyDao {
    @Query("SELECT * FROM todo where date = :date ")
     fun getTodo( date : LocalDate) : LiveData<List<Todo>>

     @Insert
     suspend fun addTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE ID=:id ")
      fun deleteTodo( id : Int)

      @Query("UPDATE todo set title = :title WHERE ID=:id ")
      fun updateTodo( id : Int, title : String)
}