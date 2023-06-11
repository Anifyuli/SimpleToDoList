package com.anifyuli.simpletodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(val todo: ArrayList<ToDoModels.Data>,
val listener: OnAdapterListener):
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textToDo = view.findViewById<TextView>(R.id.text_todo)
        val deleteIcon = view.findViewById<ImageView>(R.id.delete_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_todo, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = todo[position]
        holder.textToDo.text = data.did
        holder.itemView.setOnClickListener{
            listener.onUpdate(data)
        }
        holder.deleteIcon.setOnClickListener{
            listener.onDelete(data)
        }
    }

    override fun getItemCount() = todo.size

    public fun setData(data: List<ToDoModels.Data>){
        todo.clear()
        todo.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onUpdate(did : ToDoModels.Data)
        fun onDelete(did: ToDoModels.Data)
    }
}