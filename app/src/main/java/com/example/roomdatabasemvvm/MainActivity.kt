package com.example.roomdatabasemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.roomdatabasemvvm.data.NoteDatabase

import com.example.roomdatabasemvvm.presentation.AddNoteScreen1

import com.example.roomdatabasemvvm.presentation.NoteScreen1
import com.example.roomdatabasemvvm.presentation.NoteViewModel
import com.example.roomdatabasemvvm.ui.theme.RoomDatabaseMvvmTheme

class MainActivity : ComponentActivity() {
    // create an instance of database
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

    // create an instance of view_model
    val viewmodel by viewModels<NoteViewModel> (factoryProducer = {
        object :ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(database.dao) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDatabaseMvvmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   val state by viewmodel.state.collectAsState()
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = "NoteScreen1"){
                        composable("NoteScreen1"){
                            NoteScreen1(
                                state = state,navController = navController, onEvent = viewmodel::onEvent
                            )
                        }
                        composable("AddNoteScreen1"){
                            AddNoteScreen1(state = state,navController = navController,onEvent =viewmodel::onEvent)
                        }

                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomDatabaseMvvmTheme {

    }
}