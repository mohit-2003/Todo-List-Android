package com.tgm.todolist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgm.todolist.adapter.TodoListAdapter
import com.tgm.todolist.databinding.FragmentMainBinding
import com.tgm.todolist.room.NotesDatabase
import com.tgm.todolist.room.entity.Notes
import com.tgm.todolist.viewmodel.NotesDBViewModel
import com.tgm.todolist.viewmodel.NotesDBViewModelFactory

class MainFragment : Fragment(), TodoListAdapter.onItemClicked {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var adapter: TodoListAdapter
    lateinit var viewModel: NotesDBViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = NotesDBViewModelFactory(application)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[viewModel::class.java]

        binding.todoListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = TodoListAdapter(ArrayList(), requireContext(), this)
        binding.todoListRecyclerView.adapter = adapter

        viewModel.getAllNotes().observe(requireActivity()) {
            adapter.updateList(it as ArrayList<Notes>)
        }

        return binding.root
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