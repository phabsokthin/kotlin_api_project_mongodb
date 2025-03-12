package com.example.fetchapimongoddb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchapimongoddb.Adapter.CourseAdapter
import com.example.fetchapimongoddb.Model.CourseModel
import com.example.fetchapimongoddb.Service.ApiClient
import com.example.fetchapimongoddb.databinding.ActivityMainBinding
import okhttp3.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  val courseList = mutableListOf<CourseModel>()
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var btnAddNew: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnAddNew = findViewById(R.id.btnAddNew)
        btnAddNew.setOnClickListener {
            val intent = Intent(this, AddNew::class.java)
            startActivity(intent)
        }


        fetchCourse()
        showRecyclerView();


    }

    private fun showRecyclerView(){
        courseAdapter = CourseAdapter(this, courseList)
        binding.courseMainRecycler.layoutManager = LinearLayoutManager(this)
        binding.courseMainRecycler.adapter = courseAdapter
    }

    private fun fetchCourse(){
        ApiClient.apiService.getCourse("getUser").enqueue(object : Callback<List<CourseModel>> {
            override fun onResponse(
                call: retrofit2.Call<List<CourseModel>>,
                response: retrofit2.Response<List<CourseModel>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        courseList.clear()
                        courseList.addAll(it) //take limit data only 10
                        courseAdapter.notifyItemChanged(1)
                    }
                }
                else{
                    showToast("Failed to load ${response.code()}")
                    println("Failed to load ${response.code()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<CourseModel>>, t: Throwable) {
                println("Failed fet post ${t.message}")
                showToast("falid fetch ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    //for pass result





}