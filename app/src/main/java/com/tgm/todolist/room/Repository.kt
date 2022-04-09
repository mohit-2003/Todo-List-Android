package com.tgm.todolist.room

import androidx.lifecycle.LiveData
import com.tgm.todolist.room.entity.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dao: NotesDAO) {

    suspend fun insert(notes: Notes) = dao.insert(notes)

    suspend fun delete(notes: Notes) = dao.delete(notes)

    suspend fun update(notes: Notes) = dao.update(notes)

    fun getAllNotes(): LiveData<List<Notes>> = dao.getAllNotes()

}