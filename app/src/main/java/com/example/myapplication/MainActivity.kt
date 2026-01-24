package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var studentList: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()

    private val studentDetailsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val position = result.data?.getIntExtra("student_position", -1)
                val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("updated_student", Student::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    result.data?.getParcelableExtra("updated_student")
                }

                if (position != null && position != -1 && updatedStudent != null) {
                    students[position] = updatedStudent
                    adapter.notifyItemChanged(position)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        studentList = findViewById(R.id.student_list)
        studentList.layoutManager = LinearLayoutManager(this)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        studentList.addItemDecoration(itemDecoration)

        students.addAll(listOf(
            Student("Alice", "12345", android.R.drawable.sym_def_app_icon, false, "111-111", "Address 1"),
            Student("Bob", "23456", android.R.drawable.sym_def_app_icon, false, "222-222", "Address 2"),
            Student("Charlie", "34567", android.R.drawable.sym_def_app_icon, false, "333-333", "Address 3"),
            Student("David", "45678", android.R.drawable.sym_def_app_icon, false, "444-444", "Address 4"),
            Student("Eve", "56789", android.R.drawable.sym_def_app_icon, false, "555-555", "Address 5"),
            Student("Frank", "67890", android.R.drawable.sym_def_app_icon, false, "666-666", "Address 6"),
            Student("Grace", "78901", android.R.drawable.sym_def_app_icon, false, "777-777", "Address 7"),
            Student("Henry", "89012", android.R.drawable.sym_def_app_icon, false, "888-888", "Address 8"),
            Student("Ivy", "90123", android.R.drawable.sym_def_app_icon, false, "999-999", "Address 9")
        ))

        adapter = StudentAdapter(
            students,
            onItemClick = { _, position ->
                val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                    putExtra("student", students[position]) // Get the most up-to-date student
                    putExtra("student_position", position)
                }
                studentDetailsLauncher.launch(intent)
            },
            onCheckboxClick = { student, isChecked ->
                val index = students.indexOfFirst { it.id == student.id }
                if (index != -1) {
                    students[index] = students[index].copy(isChecked = isChecked)
                }
            }
        )
        studentList.adapter = adapter
    }
}
