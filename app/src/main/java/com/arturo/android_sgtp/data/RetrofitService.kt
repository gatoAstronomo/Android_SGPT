package com.arturo.android_sgtp.data;
import com.arturo.android_sgtp.data.model.Task;

import retrofit2.Retrofit
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RetrofitService {
    @GET("tasks")
    suspend fun getTasks(
        @Query("rut") rut: String
    ): Response<List<Task>>
}

object RetrofitServiceFactory {
    private const val BASE_URL = "http://44.197.32.169:8081/"
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}