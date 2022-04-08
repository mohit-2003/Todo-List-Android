package com.tgm.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.tgm.todolist.R
import com.tgm.todolist.model.TodoListModel
import com.tgm.todolist.viewmodel.TodoListViewModel

class TodoListAdapter(private val arrayList: ArrayList<TodoListModel>,
        private val context: Context, private val onClick: onItemClicked): RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

//    private lateinit var binding: TodoItemDesignBinding

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val title: TextView = itemView.findViewById(R.id.todo_list_title)
       val dropDownIcon: ImageView = itemView.findViewById(R.id.drop_down_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item_design, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = arrayList[position].title
        holder.dropDownIcon.setOnClickListener {
            onClick.onDropDown()
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updateList(list: ArrayList<TodoListModel>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }
    interface onItemClicked {
        fun onDropDown();
        fun onDelete();
        fun onPin();
    }
}