package com.example.learningroom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate
import java.util.Date


class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString() // Convert LocalDate to String
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) } // Convert String to LocalDate
    }
}