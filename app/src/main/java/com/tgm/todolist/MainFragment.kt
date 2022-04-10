package com.tgm.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgm.todolist.adapter.TodoListAdapter
import com.tgm.todolist.databinding.FragmentMainBinding
import com.tgm.todolist.room.entity.Notes
import com.tgm.todolist.viewmodel.AddTaskViewModel
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
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[NotesDBViewModel::class.java]

        binding.todoListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = TodoListAdapter(ArrayList(), requireContext(), this)
        binding.todoListRecyclerView.adapter = adapter

        viewModel.getAllNotes().observe(requireActivity()) {
            adapter.updateList(it as ArrayList<Notes>)
        }

        binding.addTaskFab.setOnClickListener {
            // empty object
           goToAddNotesFragment(Notes(id = -1, title = "", description = "", addedTime = ""))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionMenuClicked(notes: Notes, position: Int) {
        val popupMenu = PopupMenu(requireContext(), binding.todoListRecyclerView[position].findViewById(R.id.option_menu))
        popupMenu.inflate(R.menu.item_popup_menu)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.delete_item -> {
                        viewModel.deleteNotesById(notes.id)
                        Toast.makeText(requireContext(), "Item Deleted..", Toast.LENGTH_SHORT).show()
                        return true
                    }

                    R.id.edit_item -> {
                        goToAddNotesFragment(notes)
                        return true
                    }

                    R.id.pin_item -> {
                        Toast.makeText(requireContext(), "Item Pinned..", Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }

    override fun onItemClicked(notes: Notes, position: Int) {
        goToAddNotesFragment(notes)
    }

    private fun goToAddNotesFragment(notes: Notes){
        val addTaskViewModel = ViewModelProvider(requireActivity())[AddTaskViewModel::class.java]
        addTaskViewModel.currentNotes.value = notes

        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                    as NavHostFragment
        val navController: NavController = navHostFragment.navController
        navController.navigate(R.id.action_MainFragment_to_AddTaskFragment)
    }
}