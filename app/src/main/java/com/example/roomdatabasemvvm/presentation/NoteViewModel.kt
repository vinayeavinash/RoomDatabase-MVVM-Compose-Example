package com.example.roomdatabasemvvm.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabasemvvm.data.Note
import com.example.roomdatabasemvvm.data.NoteDuo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(private val duo: NoteDuo) : ViewModel() {
    private val isSortedByDateAdded = MutableStateFlow(true)

    //manage filter
    @OptIn(ExperimentalCoroutinesApi::class)
    var notes = isSortedByDateAdded.flatMapLatest { sort ->
        if (sort) {
            duo.getNotesOrderByAdded()
        } else {
            duo.getNotesOrderByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // declare an internal state of the view_model
   private val  _state = MutableStateFlow(NoteState())

    //combine state flows
    val state =
        combine(_state, isSortedByDateAdded, notes) { state, isSortedByDateAdded, notes ->
            state.copy(notes = notes)

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    // handle user events
    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    duo.deleteNote (event.note)
                }
            }

            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    duo.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")

                    )
                }
            }

            NotesEvent.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value

            }
        }
    }
}