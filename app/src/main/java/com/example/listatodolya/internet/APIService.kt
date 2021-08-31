package com.example.listatodolya.internet

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun obtenerFrasesGatos(@Url url:String):Response<gatosresponse>
}