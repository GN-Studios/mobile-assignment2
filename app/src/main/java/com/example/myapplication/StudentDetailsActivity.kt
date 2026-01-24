package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var student: Student
    private var studentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val receivedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("student", Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Student>("student")
        }
        studentPosition = intent.getIntExtra("student_position", -1)

        if (receivedStudent != null) {
            student = receivedStudent
            supportActionBar?.title = "Student Details"
            findViewById<ImageView>(R.id.student_detail_image).setImageResource(student.image)
            findViewById<TextView>(R.id.student_detail_name).text = "name: ${student.name}"
            findViewById<TextView>(R.id.student_detail_id).text = "id: ${student.id}"
            findViewById<TextView>(R.id.student_detail_phone).text = "phone: ${student.phone}"
            findViewById<TextView>(R.id.student_detail_address).text = "address: ${student.address}"
            val checkbox = findViewById<CheckBox>(R.id.student_detail_checked)
            checkbox.isChecked = student.isChecked
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked = isChecked
            }
        }

        findViewById<Button>(R.id.edit_button).setOnClickListener {
            Toast.makeText(this, "Edit button clicked", Toast.LENGTH_SHORT).show()
        }

        onBackPressedDispatcher.addCallback(this) {
            val resultIntent = Intent().apply {
                putExtra("updated_student", student)
                putExtra("student_position", studentPosition)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
