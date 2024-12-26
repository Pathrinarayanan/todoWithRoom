package com.example.learningroom

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.learningroom.ui.theme.LearningRoomTheme

class MainActivity : ComponentActivity() {
    lateinit var viewmodel: MainViewmodel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)
        setContent {
            var isAdd by remember { mutableStateOf(false) }
            var title by remember { mutableStateOf("") }
            LearningRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(content = {
                            Icon(Icons.Filled.Add, contentDescription = null)
                        },
                            onClick = {
                                isAdd = true;
                            }
                            )
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        DayCalendar(viewmodel)
                        ItemDetailsPage( viewmodel)
                    }
                }
                if(isAdd){
                    AlertDialog(
                        onDismissRequest = { isAdd = false },
                        title = {
                            Text("Add Item") },
                        text = {
                            TextField(value = title, onValueChange = {
                            title = it
                        }, placeholder = {
                                Text("Enter title")
                            }
                                )},
                        confirmButton = {
                            Button(
                                onClick = {
                                    // Handle the confirm button click here
                                    viewmodel.addTodo(title)
                                    title = ""
                                    isAdd = false

                                }
                            ) {
                                Text("Add")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    // Handle the dismiss button click here
                                    isAdd = false
                                }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )

                }
            }
        }
    }

}