package com.example.roomdatabasemvvm.presentation

import com.example.roomdatabasemvvm.data.Note

sealed interface NotesEvent {
    object  SortNotes:NotesEvent
    data class  SaveNote(val note: Note):NotesEvent
    data class DeleteNote(val title:String,val description:String):NotesEvent
}