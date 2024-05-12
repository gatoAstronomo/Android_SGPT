package com.arturo.android_sgtp.data

import com.arturo.android_sgtp.data.model.Task
import retrofit2.HttpException

import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

interface RetrofitService {
    @GET("tasks")
    suspend fun getTasks(
        @Query("rut") rut: Int
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

class TaskRepository private constructor() {

    private val retrofitService = RetrofitServiceFactory.makeRetrofitService()

    suspend fun getTasks(rut: Int): List<Task> {
        return try {
            retrofitService.getTasks(rut).body() ?: emptyList()
        } catch (e: Exception) {
            // Manejar errores de red u otros errores
            listOf(
                Task("Error al descargar sus tareas", "", ""),
                Task("Error al descargar sus tareas", "", ""),
            )
        }
    }

    companion object {
        @Volatile
        private var instance: TaskRepository? = null
        fun getInstance(): TaskRepository {
            return instance ?: synchronized(this) {
                instance ?: TaskRepository().also { instance = it }
            }
        }
    }
}

