package com.tgm.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tgm.todolist.R
import com.tgm.todolist.room.entity.Notes

class TodoListAdapter(private val arrayList: ArrayList<Notes>,
        private val context: Context, private val onClick: onItemClicked): RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

//    private lateinit var binding: TodoItemDesignBinding

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val title: TextView = itemView.findViewById(R.id.todo_list_title)
       val optionMenu: TextView = itemView.findViewById(R.id.option_menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item_design, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = arrayList[position].title
        holder.optionMenu.setOnClickListener {
            onClick.onOptionMenuClicked(arrayList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updateList(list: ArrayList<Notes>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    interface onItemClicked {
        fun onOptionMenuClicked(notes: Notes, position: Int)
    }
}