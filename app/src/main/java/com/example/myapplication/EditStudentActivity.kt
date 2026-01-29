package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityEditStudentBinding

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val receivedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("student_to_edit", Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("student_to_edit")
        }

        if (receivedStudent != null) {
            student = receivedStudent
            binding.editStudentName.setText(student.name)
            binding.editStudentId.setText(student.id)
            binding.editStudentPhone.setText(student.phone)
            binding.editStudentAddress.setText(student.address)
            binding.editStudentChecked.isChecked = student.isChecked
        }

        binding.saveButton.setOnClickListener {
            val updatedStudent = student.copy(
                name = binding.editStudentName.text.toString(),
                id = binding.editStudentId.text.toString(),
                phone = binding.editStudentPhone.text.toString(),
                address = binding.editStudentAddress.text.toString(),
                isChecked = binding.editStudentChecked.isChecked
            )

            val resultIntent = Intent().apply {
                putExtra("updated_student", updatedStudent)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}