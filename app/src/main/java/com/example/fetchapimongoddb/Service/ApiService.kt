package com.example.fetchapimongoddb.Service

import com.example.fetchapimongoddb.Model.CourseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {


    @POST("{endpoint}")
    fun postData(@Path("endpoint") endpoint: String, @Body course: CourseModel): Call<CourseModel>

    //use dynamic data
    @GET("{getDataName}")
    fun getCourse(@Path("getDataName") getDataName: String): Call<List<CourseModel>>

    @PUT("{updateData}/{id}")
    fun updateData(
        @Path("updateData") updateName: String,
        @Path("id") id: String,
        @Body course: CourseModel
    ): Call<CourseModel>


    @DELETE("{deleteName}/{id}")
    fun deleteData(
        @Path("deleteName") deleteName: String,
        @Path("id") id: String
    ): Call<Void>
}