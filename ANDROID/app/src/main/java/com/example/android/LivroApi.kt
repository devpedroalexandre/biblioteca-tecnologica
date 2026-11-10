package com.example.android

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LivroApi {

    @GET("livros")
    suspend fun listarLivros(): List<Livro>

    @GET("livros/{id}")
    suspend fun buscarLivro(@Path("id") id: Long): Livro

    @POST("livros")
    suspend fun cadastrarLivro(@Body request: LivroRequest): Livro
}

object ApiClient {
    private const val URL_BASE = "http://127.0.0.1:8089/"

    val livroApi: LivroApi = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LivroApi::class.java)
}
