package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityStudentDetailsBinding

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding
    private lateinit var student: Student
    private var studentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.student_details_title)

        val receivedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("student", Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Student>("student")
        }
        studentPosition = intent.getIntExtra("student_position", -1)

        if (receivedStudent != null) {
            student = receivedStudent
            binding.studentDetailImage.setImageResource(student.image)
            binding.studentDetailName.text = getString(R.string.name_format, student.name)
            binding.studentDetailId.text = getString(R.string.id_format, student.id)
            binding.studentDetailPhone.text = getString(R.string.phone_format, student.phone)
            binding.studentDetailAddress.text = getString(R.string.address_format, student.address)
            binding.studentDetailChecked.isChecked = student.isChecked
            binding.studentDetailChecked.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked = isChecked
            }
        }

        binding.editButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.edit_button_clicked), Toast.LENGTH_SHORT).show()
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
