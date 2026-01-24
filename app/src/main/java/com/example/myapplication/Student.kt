package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    val name: String,
    val id: String,
    val image: Int,
    var isChecked: Boolean = false,
    val phone: String,
    val address: String
) : Parcelable