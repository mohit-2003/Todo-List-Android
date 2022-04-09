package com.tgm.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tgm.todolist.model.TodoListModel
import com.tgm.todolist.room.NotesDAO
import com.tgm.todolist.room.NotesDatabase
import com.tgm.todolist.room.Repository
import com.tgm.todolist.room.entity.Notes
import kotlinx.coroutines.launch

class NotesDBViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    var notes = MutableLiveData<ArrayList<Notes>>()

    init {
        val dao = NotesDatabase.getInstance(application).notesDAO
        repository = Repository(dao)
    }

    fun insertNotes(notes: Notes) {
        viewModelScope.launch {
            repository.insert(notes)
        }
    }

    fun deleteNotes(notes: Notes) {
        viewModelScope.launch {
            repository.delete(notes)
        }
    }

    fun updateNotes(notes: Notes) {
        viewModelScope.launch {
            repository.delete(notes)
        }
    }

    fun getAllNotes(): LiveData<List<Notes>> {
        return repository.getAllNotes()
    }
}