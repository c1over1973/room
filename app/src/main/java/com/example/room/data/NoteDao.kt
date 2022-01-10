package com.example.room.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM note_table")
    fun readAllData(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery")
    fun searchData(searchQuery: String): LiveData<List<Note>>
}