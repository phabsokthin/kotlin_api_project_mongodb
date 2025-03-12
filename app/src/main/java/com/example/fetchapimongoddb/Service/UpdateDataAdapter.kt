package com.example.fetchapimongoddb.Service

import android.util.Log
import com.example.fetchapimongoddb.Model.CourseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDataAdapter {
    interface UpdateDataListener {
        fun onUpdateSuccess(updatedCourse: CourseModel)
        fun onUpdateFailure(error: String)
    }

    fun updateCourse(updateName: String, id: String, course: CourseModel, listener: UpdateDataListener) {
        val call = ApiClient.apiService.updateData(updateName, id, course)

        call.enqueue(object : Callback<CourseModel> {
            override fun onResponse(call: Call<CourseModel>, response: Response<CourseModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onUpdateSuccess(it)
                    } ?: listener.onUpdateFailure("Empty response")
                } else {
                    listener.onUpdateFailure("Failed to update: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CourseModel>, t: Throwable) {
                listener.onUpdateFailure("Network error: ${t.message}")
                Log.e("UpdateDataAdapter", "Error updating: ${t.message}")
            }
        })
    }
}
