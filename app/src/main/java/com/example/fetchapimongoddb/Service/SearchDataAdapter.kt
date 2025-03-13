package com.example.fetchapimongoddb.Service

import com.example.fetchapimongoddb.Model.CourseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class SearchDataAdapter(private val listener: SearchListener) {

    interface SearchListener {
        fun onSearchResult(filteredList: List<CourseModel>)
        fun onSearchFailure(error: String)
    }

    fun searchUser(searchDataName: String ,name: String, queryValue: String) {
        val queryMap = mapOf(name to queryValue) // ✅ Dynamic Query Parameter

        val call = ApiClient.apiService.searchUser(searchDataName,queryMap)

        call.enqueue(object : Callback<List<CourseModel>> {
            override fun onResponse(call: Call<List<CourseModel>>, response: Response<List<CourseModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onSearchResult(it)
                    } ?: listener.onSearchFailure("No results found")
                } else {
                    listener.onSearchFailure("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<CourseModel>>, t: Throwable) {
                listener.onSearchFailure("Network error: ${t.message}")
            }
        })
    }
}
