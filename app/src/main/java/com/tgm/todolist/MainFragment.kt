package com.tgm.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgm.todolist.adapter.TodoListAdapter
import com.tgm.todolist.databinding.FragmentMainBinding
import com.tgm.todolist.model.TodoListModel
import com.tgm.todolist.viewmodel.TodoListViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(), TodoListAdapter.onItemClicked {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val list: ArrayList<TodoListModel> = ArrayList()
    private lateinit var viewModel: TodoListViewModel
    lateinit var adapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[TodoListViewModel::class.java]

//        list.clear()
//        list.add(TodoListModel("Sample Title 1", ""))
//        list.add(TodoListModel("Sample Title 2", ""))
//        list.add(TodoListModel("Sample Title 3", ""))
//        list.add(TodoListModel("Sample Title 4", ""))
//        list.add(TodoListModel("Sample Title 5", ""))

        viewModel.todoList.value = list
        binding.todoListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = TodoListAdapter(list, requireContext(), this)
        binding.todoListRecyclerView.adapter = adapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.todoList.observe(requireActivity()) {
//            adapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDropDown() {
        Toast.makeText(requireContext(), "Clicked..", Toast.LENGTH_SHORT).show()
    }

    override fun onDelete() {
        Toast.makeText(requireContext(), "Clicked..", Toast.LENGTH_SHORT).show()
    }

    override fun onPin() {
        Toast.makeText(requireContext(), "Clicked..", Toast.LENGTH_SHORT).show()
    }
}