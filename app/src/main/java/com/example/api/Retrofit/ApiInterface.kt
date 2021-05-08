package com.example.api.Retrofit

import com.example.api.model.MyData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("v1/patients/16/vitals/temperature")
    fun getData():Call<List<MyData>>
}