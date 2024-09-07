package com.example.marsphotosapp.network

import retrofit2.Response
import retrofit2.http.GET


interface MarsApiService{
    @GET ("photos")
    suspend fun getPhotos() : Response<List<MarsPhoto>>
}