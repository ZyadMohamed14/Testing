package com.example.testing.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String ="41068541-614917d34e2fb6f2ee7bed468"
    ): Response<ImageResponse>
}