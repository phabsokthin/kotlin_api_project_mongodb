package com.example.fetchapimongoddb.Service

import com.example.fetchapimongoddb.Model.CourseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

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


    //search once
//    @GET("{searchDataName}")
//    fun searchUser(@Path("searchDataName") searchDataName: String,  @Query("name") name: String): Call<List<CourseModel>>

    //search multi
    @GET("{searchDataName}")
    fun searchUser(
        @Path("searchDataName") searchDataName: String,
        @QueryMap queryMap: Map<String, String> // ✅ Dynamic query parameters
    ): Call<List<CourseModel>>
}