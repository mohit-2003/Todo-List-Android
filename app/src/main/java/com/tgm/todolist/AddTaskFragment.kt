package com.tgm.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tgm.todolist.databinding.FragmentAddTaskBinding
import com.tgm.todolist.room.NotesDatabase
import com.tgm.todolist.room.entity.Notes
import com.tgm.todolist.viewmodel.NotesDBViewModel
import com.tgm.todolist.viewmodel.NotesDBViewModelFactory

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)


        val application = requireNotNull(this.activity).application
        val dao = NotesDatabase.getInstance(application).notesDAO
        val viewModelFactory = NotesDBViewModelFactory(dao, application)
        val NotesDBViewModel = ViewModelProvider(this, viewModelFactory)[NotesDBViewModel::class.java]

        binding.addNowBtn.setOnClickListener {
            NotesDBViewModel.insertNotes(Notes(title = binding.titleOfTodo.editText?.text.toString(),
            description = binding.descOfTodo.editText?.text.toString(), addedTime = ""))
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}