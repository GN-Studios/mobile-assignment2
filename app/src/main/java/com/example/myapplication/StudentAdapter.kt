package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: List<Student>,
    private val onItemClick: (Student, Int) -> Unit,
    private val onCheckboxClick: (Student, Boolean) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student, onCheckboxClick)
        holder.itemView.setOnClickListener {
            onItemClick(student, position)
        }
    }

    override fun getItemCount() = students.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.student_name)
        private val idTextView: TextView = itemView.findViewById(R.id.student_id)
        private val imageView: ImageView = itemView.findViewById(R.id.student_image)
        private val checkbox: CheckBox = itemView.findViewById(R.id.student_checkbox)

        fun bind(student: Student, onCheckboxClick: (Student, Boolean) -> Unit) {
            nameTextView.text = student.name
            idTextView.text = student.id
            imageView.setImageResource(student.image)
            checkbox.isChecked = student.isChecked

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckboxClick(student, isChecked)
            }
        }
    }
}