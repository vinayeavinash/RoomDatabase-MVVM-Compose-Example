package com.example.roomdatabasemvvm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDuo {
    @Upsert
    suspend fun upsertNote(note: Note)
    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY dateAdded")
    fun getNotesOrderByAdded(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY title")
    fun getNotesOrderByTitle(): Flow<List<Note>>

}