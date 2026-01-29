package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAddStudentBinding

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveButton.setOnClickListener {
            val name = binding.addStudentName.text.toString()
            val id = binding.addStudentId.text.toString()
            val phone = binding.addStudentPhone.text.toString()
            val address = binding.addStudentAddress.text.toString()
            val isChecked = binding.addStudentChecked.isChecked

            val newStudent = Student(name, id, android.R.drawable.sym_def_app_icon, isChecked, phone, address)

            val resultIntent = Intent().apply {
                putExtra("new_student", newStudent)
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