package com.example.fetchapimongoddb

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fetchapimongoddb.Model.CourseModel
import com.example.fetchapimongoddb.Service.PostDataAdapter

class AddNew : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new)

        initNewUser()
    }

    private fun initNewUser() {
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnSubmit = findViewById(R.id.btnLogin)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "User Name required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newCourse = CourseModel(_id = "",name, email)

            val postDataAdapter = PostDataAdapter()
            postDataAdapter.createData("post", newCourse, object : PostDataAdapter.PostDataListener {
                override fun onPostSuccess(response: CourseModel) {
                    Toast.makeText(this@AddNew, "Added successfully!", Toast.LENGTH_SHORT).show()

                    // pass data back

                    val intent = Intent(this@AddNew, MainActivity::class.java)
                    startActivity(intent)

                }

                override fun onPostFailure(error: String) {
                    Toast.makeText(this@AddNew, "Failed to add: $error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
