package com.example.room.data

import androidx.lifecycle.LiveData

class Repository(private val noteDao: NoteDao){
    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun deleteAllData(){
        noteDao.deleteAllData()
    }

    fun searchData(searchQuery: String): LiveData<List<Note>>{
        return  noteDao.searchData(searchQuery)
    }
}