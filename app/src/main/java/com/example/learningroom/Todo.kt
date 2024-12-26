package com.example.learningroom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.Instant
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "todo")
@TypeConverters(Converters::class)
data class Todo @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title : String,
    var time : Long = System.currentTimeMillis(),
    var date : LocalDate = LocalDate.now()
)