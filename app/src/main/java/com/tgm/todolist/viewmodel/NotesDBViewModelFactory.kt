package com.tgm.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tgm.todolist.room.NotesDAO
import com.tgm.todolist.room.NotesDatabase

class NotesDBViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesDBViewModel::class.java)) {
            return NotesDBViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}