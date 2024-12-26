package com.example.learningroom

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class MainViewmodel : ViewModel() {
    private var todoDao = MainApplication.todoDb.getTodoDao()
     var searchText  by mutableStateOf("")
     var dateClicked  by mutableStateOf(LocalDate.now())
    fun getTodo(): LiveData<List<Todo>> {

        return todoDao.getTodo(dateClicked)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo(title : String){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(
                Todo(title =title))

        }
    }
    fun updateTodo(title : String, id  : Int){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.updateTodo(title =title,id =id)


        }
    }

    fun deleteTodo(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }
    }
}