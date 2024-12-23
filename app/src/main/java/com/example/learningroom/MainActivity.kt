package com.example.learningroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.learningroom.ui.theme.LearningRoomTheme

class MainActivity : ComponentActivity() {
    lateinit var viewmodel: MainViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewmodel = ViewModelProvider(this).get(MainViewmodel::class.java)
        setContent {
            LearningRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(innerPadding)
                }
            }
        }
    }


    @Composable
    fun MainView(paddingValues: PaddingValues) {
        Column(
            modifier = Modifier.padding(paddingValues).background(Color.White)
                .padding(horizontal = 20.dp).padding(vertical = 16.dp).wrapContentHeight().fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                   viewmodel.text,
                    onValueChange = {
                        viewmodel.text = it
                    },

                    modifier = Modifier.width(250.dp)

                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            shape = RoundedCornerShape(8.dp),
                            width = 2.dp,
                            color = Color.DarkGray
                        )
                        .padding(horizontal = 20.dp)
                        .padding(top = 5.dp)

                )
                Box(
                    modifier = Modifier.height(30.dp).width(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Cyan)
                        .clickable {
                            viewmodel.addTodo(viewmodel.text)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("ADD")
                }
            }
            val todos by viewmodel.getTodo().observeAsState(emptyList())

            LazyColumn(modifier = Modifier.height(500.dp).fillMaxWidth()) {

                       todos.forEach{
                        item{
                            Items(it)

                        }

               }
            }
        }
    }

    @Composable
    fun Items(todo: Todo) {
        Row(
            modifier = Modifier.background(Color.White).padding(vertical = 16.dp).fillMaxWidth()
                .wrapContentHeight(), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(todo.title, fontSize = 12.sp)
            Box {
                Text("Delete", color = Color.Red , modifier  = Modifier.clickable {
                    viewmodel.deleteTodo(todo.id)
                })
            }
        }
    }
}