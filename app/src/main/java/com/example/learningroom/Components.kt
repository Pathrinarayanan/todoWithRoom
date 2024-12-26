package com.example.learningroom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchBox(viewmodel: MainViewmodel) {
    Row(modifier  = Modifier.fillMaxWidth().padding(bottom = 20.dp).height(40.dp).clip(RoundedCornerShape(20.dp))
        .background(Color.White)
        .border(shape = RoundedCornerShape(20.dp), color = Color.DarkGray, width = 1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.search_icon), contentDescription = null,
            modifier = Modifier.padding(start = 16.dp).size(16.dp)
        )
        Box {

                BasicTextField(
                    value = viewmodel.searchText,
                    onValueChange = {
                        viewmodel.searchText = it
                    },

                    textStyle = TextStyle(color = Color.LightGray),
                )
            if (viewmodel.searchText == "") {
                Text("Search", color = Color.LightGray)
            }
        }
        }
    }



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDetailsPage( viewmodel: MainViewmodel) {
    val todos by viewmodel.getTodo().observeAsState(emptyList())
    val filteredTodos = todos.filter {todo->
        todo.title.contains(viewmodel.searchText,true)
    }
    val scrollstate = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFfffdf4))
        .padding( 10.dp)

    ){
        SearchBox(viewmodel)
        Text(
            if(viewmodel.dateClicked == LocalDate.now())"Today's Tasks" else viewmodel.dateClicked.dayOfMonth.toString()+" " + viewmodel.dateClicked.month + " " + viewmodel.dateClicked.year+" 's Task",
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.verticalScroll(
            scrollstate
        )) {
            filteredTodos.forEach {todo->
                Task(todo,
                    deleteTask = { id->
                        viewmodel.deleteTodo(id)
                    },
                    updateTask = {title ,id->
                        viewmodel.updateTodo(title,id)
                    }
                )
            }
        }

    }
}


@Composable
fun Task(todo: Todo, deleteTask:  (Int) -> Unit, updateTask : (String, Int) ->Unit) {

    var checkedState by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var updatedText = remember{ mutableStateOf(todo.title) }
    var dateFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
    var date = Date(todo.time)
    var finalDate = dateFormat.format(date)
    AnimatedVisibility(showEditDialog){
        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
            },
            confirmButton = {
                Button (onClick = {
                    updateTask(updatedText.value, todo.id)
                    showEditDialog = false
                }){
                    Text("Save")
                }

            },
            dismissButton = {
                Button (onClick = {
                    showEditDialog = false
                }){
                    Text("Cancel")
                }
            },
            title = {
                Text("Edit the task!")
            },
            text = {
                BasicTextField(
                    value = updatedText.value,
                    onValueChange = {
                        updatedText.value = it
                    }
                )
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // .background(Color(0xFFd6d6d6))
            .clip(RoundedCornerShape(20.dp))
            .border(shape = RoundedCornerShape(20.dp), color = Color(0xFFD6D6D6), width = 2.dp)
            .background(Color(0xFFfffdf4))

            .clickable {

                checkedState = !checkedState
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState, onCheckedChange = {
             //   Toast.makeText(context, "Checkbox check", Toast.LENGTH_SHORT).show()
                checkedState = it
            },
            colors = CheckboxColors(
                checkedBoxColor = Color(0xFFDFBD43),
                uncheckedBoxColor = Color.White,
                checkedBorderColor = Color.Black,
                checkedCheckmarkColor = Color.White,
                disabledBorderColor = Color.Black,
                uncheckedBorderColor = Color.Black,
                disabledCheckedBoxColor = Color.White,
                disabledIndeterminateBoxColor = Color.White,
                disabledUncheckedBoxColor = Color.White,
                uncheckedCheckmarkColor = Color.White,
                disabledUncheckedBorderColor = Color.White,
                disabledIndeterminateBorderColor = Color.White
            )
        )
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                Text(
                    text = finalDate.toString(),
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.weight(0.8f)
                )
                Image(
                    painter = painterResource(R.drawable.edit_icon),
                    contentDescription = "edit",
                    modifier = Modifier.padding(end = 12.dp)
                        .clickable {
                            showEditDialog = true
                        }
                )
            }
            Row {
                Text(
                    text = todo.title,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.weight(0.8f),
                    style = if(checkedState) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                )
                Image(
                    painter = painterResource(R.drawable.delete_icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 20.dp)
                        .clickable {
                            deleteTask(todo.id)
                        }

                )
            }
        }

    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCalendar(viewmodel: MainViewmodel) {
    // Get the current month and year
    var currentDate by remember { mutableStateOf( LocalDate.now())}
    // Get the list of days in the current month

    val daysInMonth = remember(currentDate) {
        getDaysInMonth(currentDate.year, currentDate.monthValue)
    }

    // Create a scrollable row to display days
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight() .background(Color(0xFFfffdf4)).padding(16.dp)) {
        Row( verticalAlignment = Alignment.CenterVertically,) {
            Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier.clickable {
                currentDate = currentDate.minusMonths(1)
            })
            Text(
                text = "Month: ${currentDate.month.name} ${currentDate.year}",
                modifier = Modifier.padding(20.dp)
            )
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.clickable {
                    currentDate = currentDate.plusMonths(1)
                })
        }
val state = rememberLazyListState()

        LaunchedEffect (Unit){
            state.scrollToItem(currentDate.dayOfMonth-1)
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            state = state
        ) {
            items(daysInMonth.size) { index ->
                val day = daysInMonth[index]
                DayView(day, viewmodel)
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayView(day: LocalDate, viewmodel: MainViewmodel) {
    val dayText = remember {  day.format(DateTimeFormatter.ofPattern("d"))}
    Column(verticalArrangement = Arrangement.spacedBy(15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(
                    if (viewmodel.dateClicked == day) Color(0XFFDFBD43) // Highlighted color
                    else Color(0xFF4D4117) // Default color
                )
                .clickable {
                    viewmodel.dateClicked = day
                }
            ,
        ) {
            Text(
                text = dayText,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
        Text(day.dayOfWeek.name.toString().take(3))
    }
}

// Helper function to get the days of the month
@RequiresApi(Build.VERSION_CODES.O)
fun getDaysInMonth(year: Int, month: Int): List<LocalDate> {
    val startOfMonth = LocalDate.of(year, month, 1)
    val lengthOfMonth = startOfMonth.lengthOfMonth()

    return List(lengthOfMonth) { startOfMonth.plusDays(it.toLong()) }
}

