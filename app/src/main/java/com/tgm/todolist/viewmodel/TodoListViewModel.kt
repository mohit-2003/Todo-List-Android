package com.tgm.todolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tgm.todolist.model.TodoListModel

class TodoListViewModel: ViewModel() {
    var todoList = MutableLiveData<ArrayList<TodoListModel>>()

    fun addTask(todoListModel: TodoListModel){
        todoList.value?.add(todoListModel)
    }
}