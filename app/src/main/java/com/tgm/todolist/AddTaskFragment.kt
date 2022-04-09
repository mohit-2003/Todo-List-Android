package com.tgm.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tgm.todolist.databinding.FragmentAddTaskBinding
import com.tgm.todolist.room.NotesDatabase
import com.tgm.todolist.room.entity.Notes
import com.tgm.todolist.viewmodel.AddTaskViewModel
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
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = NotesDBViewModelFactory(application)
        // for database operations
        val viewModel = ViewModelProvider(this, viewModelFactory)[NotesDBViewModel::class.java]
        // for layout (set data)
        binding.addTaskViewModel = ViewModelProvider(requireActivity())[AddTaskViewModel::class.java]

        binding.addNowBtn.setOnClickListener {
            if (checkValidity()) {
                viewModel.insertNotes(
                    Notes(
                        title = binding.titleOfTodo.editText?.text.toString(),
                        description = binding.descOfTodo.editText?.text.toString(), addedTime = ""
                    )
                )
                clearInputBox()
                Toast.makeText(requireContext(), "Added Successfully...", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun checkValidity(): Boolean {
        if (binding.titleOfTodo.editText?.text.toString() == ""){
            binding.titleOfTodo.isErrorEnabled = true
            return false
        }
        return true
    }

    private fun clearInputBox() {
        binding.titleOfTodo.editText?.text?.clear()
        binding.descOfTodo.editText?.text?.clear()

        binding.descOfTodo.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}