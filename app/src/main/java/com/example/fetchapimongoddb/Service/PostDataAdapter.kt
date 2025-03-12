package com.example.fetchapimongoddb.Service


import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.fetchapimongoddb.Model.CourseModel

class PostDataAdapter {

    interface PostDataListener {
        fun onPostSuccess(response: CourseModel)
        fun onPostFailure(error: String)
    }

    fun createData(postName: String, course: CourseModel, listener: PostDataListener) {
        val call = ApiClient.apiService.postData(postName, course)

        call.enqueue(object : Callback<CourseModel> {
            override fun onResponse(call: Call<CourseModel>, response: Response<CourseModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onPostSuccess(it)
                    } ?: listener.onPostFailure("Empty response")
                } else {
                    listener.onPostFailure("Failed to post: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CourseModel>, t: Throwable) {
                listener.onPostFailure("Network error: ${t.message}")
                Log.e("PostDataAdapter", "Error posting data: ${t.message}")
            }
        })
    }
}
