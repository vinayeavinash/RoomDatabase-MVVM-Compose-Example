package com.example.roomdatabasemvvm.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.time.format.TextStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen1(state: NoteState, navController: NavController, onEvent: (NotesEvent) -> Unit) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onEvent(
                NotesEvent.SaveNote(
                    title = state.title.value,
                    description = state.description.value
                )
            )
            navController.popBackStack()
        }) {
            Icon(imageVector = Icons.Rounded.Check, contentDescription = "Save Note")
        }
    }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().padding(16.dp).clip(RoundedCornerShape(15.dp)),
                value = state.title.value,
                onValueChange = { state.title.value = it },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                ),
                placeholder = {
                    Text(text = "Title")
                })
            TextField(modifier = Modifier.fillMaxWidth().padding(16.dp).clip(RoundedCornerShape(15.dp)),
                value = state.description.value,
                onValueChange = { state.description.value = it },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ), placeholder = { Text(text = "Description") })

        }
    }
}

@Preview(name = "AddNoteScreen Preview")
@Composable
fun AddNoteScreenPreview1() {
    // Initialize the state with default values for preview
    val noteState = remember { NoteState() }

    // Mock NavController for preview
    val navController = rememberNavController()

    // Preview the AddNoteScreen composable
    AddNoteScreen1(
        state = noteState,
        navController = navController,
        onEvent = {}
    )
}