package com.arturo.android_sgtp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arturo.android_sgtp.R
import com.arturo.android_sgtp.data.model.Task

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombre_text_view)
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcion_text_view)
        val hechaTextView: TextView = itemView.findViewById(R.id.hecha_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.nombreTextView.text = task.nombre
        holder.descripcionTextView.text = task.descripcion
        holder.hechaTextView.text = task.hecha
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}
