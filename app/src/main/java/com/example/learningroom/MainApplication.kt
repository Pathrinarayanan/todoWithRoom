package com.example.learningroom

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import com.example.learningroom.db.DatabaseRoom

class MainApplication : Application() {
    companion object {
        lateinit var todoDb: DatabaseRoom
    }
    override fun onCreate() {
        super.onCreate()
        todoDb = Room.databaseBuilder(context = applicationContext,
            name = DatabaseRoom.Name,
            klass = DatabaseRoom::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    }
}