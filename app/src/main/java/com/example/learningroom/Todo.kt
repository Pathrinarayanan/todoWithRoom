package com.example.learningroom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "todo")
data class Todo @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title : String,
    var date : Long = System.currentTimeMillis()
)