package com.example.practica5nativas.ejercicio2.api

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object ApiClient {
    private const val BASE_URL = "https://openlibrary.org/"

    private val client = OkHttpClient.Builder()
        .cache(Cache(File("cache"), (10 * 1024 * 1024).toLong()))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: OpenLibraryService = retrofit.create(OpenLibraryService::class.java)
}

