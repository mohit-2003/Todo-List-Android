package com.tgm.todolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tgm.todolist.room.entity.Notes

class AddTaskViewModel: ViewModel() {

    val currentNotes = MutableLiveData<Notes>()

}