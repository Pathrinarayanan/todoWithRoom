package com.example.learningroom.db

import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.learningroom.Converters
import com.example.learningroom.Todo

@Database(entities = [Todo::class] , version = 1)
@TypeConverters(Converters::class)
abstract class DatabaseRoom : RoomDatabase(){
    companion object{
         const val Name  ="todo_db"
    }
    // get instance of dao
    abstract fun getTodoDao() : MyDao
}