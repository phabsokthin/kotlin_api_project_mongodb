package com.example.fetchapimongoddb.Service

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteDataAdapter {

    interface DeleteDataListener {
        fun onDeleteSuccess()
        fun onDeleteFailure(error: String)
    }

    fun delete(deleteName: String, id: String, listener: DeleteDataListener) {
        val call = ApiClient.apiService.deleteData(deleteName, id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    listener.onDeleteSuccess()
                } else {
                    listener.onDeleteFailure("Failed to delete: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                listener.onDeleteFailure("Network error: ${t.message}")
                Log.e("DeleteDataAdapter", "Error deleting data: ${t.message}")
            }
        })
    }
}
