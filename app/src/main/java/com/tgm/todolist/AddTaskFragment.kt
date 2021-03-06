package com.tgm.todolist

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tgm.todolist.databinding.FragmentAddTaskBinding
import com.tgm.todolist.room.entity.Notes
import com.tgm.todolist.viewmodel.AddTaskViewModel
import com.tgm.todolist.viewmodel.NotesDBViewModel
import com.tgm.todolist.viewmodel.NotesDBViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var addTaskViewModel: AddTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = NotesDBViewModelFactory(application)
        // for database operations
        val viewModel = ViewModelProvider(this, viewModelFactory)[NotesDBViewModel::class.java]
        // for layout (set data)
        addTaskViewModel = ViewModelProvider(requireActivity())[AddTaskViewModel::class.java]
        addTaskViewModel.currentNotes.value?.addedTime = getCurrentTime()
        binding.addTaskViewModel = addTaskViewModel

        binding.addNowBtn.setOnClickListener {
            if (addTaskViewModel.currentNotes.value?.id != -1L) {
                // updating notes
                viewModel.updateNotes(
                    Notes(id = addTaskViewModel.currentNotes.value?.id ?: -1L,
                        title = binding.titleOfTodo.editText?.text.toString(),
                        description = binding.descOfTodo.editText?.text.toString(),
                        addedTime = addTaskViewModel.currentNotes.value?.addedTime.toString()
                    )
                )
                Toast.makeText(requireContext(), "Updated Successfully...", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // adding new notes
                if (checkValidity()) {
                    viewModel.insertNotes(
                        Notes(
                            title = binding.titleOfTodo.editText?.text.toString(),
                            description = binding.descOfTodo.editText?.text.toString(),
                            addedTime = addTaskViewModel.currentNotes.value?.addedTime.toString()
                        )
                    )
                    clearInputBox()
                    Toast.makeText(requireContext(), "Added Successfully...", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // only inflating when edit item clicked or item clicked
        if (addTaskViewModel.currentNotes.value?.id != -1L) {
            inflater.inflate(R.menu.edit_menu, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_item -> {
                if (!binding.addNowBtn.isEnabled){
                    item.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_close_24)
                    binding.addNowBtn.text = getString(R.string.update_now)
                    binding.addNowBtn.isEnabled = true
                } else {
                    item.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_edit_24)
                    binding.addNowBtn.text = getString(R.string.add_now)
                    binding.addNowBtn.isEnabled = false
                }
                return true
            }
            R.id.share_item_as_text -> {
                shareNotes()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareNotes() {
        val title = addTaskViewModel.currentNotes.value?.title
        val description = addTaskViewModel.currentNotes.value?.description

        val intent = Intent(Intent.ACTION_SEND)
        val shareBody = "$title\n$description"
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            getString(R.string.my_notes)
        )
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(
            Intent.createChooser(
                intent,
                "Share via"
            )
        )
    }

    private fun checkValidity(): Boolean {
        if (binding.titleOfTodo.editText?.text.toString() == "") {
            binding.titleOfTodo.isErrorEnabled = true
            return false
        }
        return true
        TODO("Check validity")
    }

    private fun clearInputBox() {
        binding.titleOfTodo.editText?.text?.clear()
        binding.descOfTodo.editText?.text?.clear()

        binding.descOfTodo.clearFocus()
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH)
        return dateFormat.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}