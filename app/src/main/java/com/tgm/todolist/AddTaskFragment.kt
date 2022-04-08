package com.tgm.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tgm.todolist.databinding.FragmentAddTaskBinding
import com.tgm.todolist.model.TodoListModel
import com.tgm.todolist.viewmodel.TodoListViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: TodoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[TodoListViewModel::class.java]

        binding.addNowBtn.setOnClickListener {
            viewModel.addTask(TodoListModel(binding.titleOfTodo.editText?.text.toString(),
                binding.descOfTodo.editText?.text.toString()))
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}