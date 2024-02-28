package com.example.roomdatabasemvvm.presentation

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Index
import com.example.roomdatabasemvvm.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen1(state: NoteState, navController: NavController, onEvent: (NotesEvent) -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .height(40.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "NoteScreen",
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 17.sp, fontWeight = FontWeight.SemiBold
                )
                IconButton(onClick = { onEvent(NotesEvent.SortNotes) }) {
                    Icon(
                        imageVector = Icons.Rounded.List,
                        contentDescription = "sort notes", modifier = Modifier.size(35.dp)
                    )

                }
            }


        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                state.title.value = ""
                state.description.value = ""
                navController.navigate("AddNoteScreen1")
            }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add new note"
                )
            }
        }) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
        )

        {
            items(state.notes.size) { index ->
                NoteItem(
                    state = state,
                    index = index,
                    onEvent = onEvent
                )
            }
        }
    }

}

@Composable
fun NoteItem(
    state: NoteState,
    index: Int,
    onEvent: (NotesEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clip(
                RoundedCornerShape(10.dp)
            )
     ) {
        Column(modifier = Modifier.weight(1f)) {


            Text(
                text = state.notes[index].title, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.notes[index].description, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )


        }


    }
}

@Preview(name = "NoteScreen Preview")
@Composable
fun NoteScreenOnePreview() {
    // Initialize the state with default values for preview
    val noteState = remember { NoteState() }

    // Mock NavController for preview
    val navController = rememberNavController()

    // Preview the NoteScreen composable
    NoteScreen1(
        state = noteState,
        navController = navController,
        onEvent = {}
    )
}