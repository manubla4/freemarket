package com.manubla.cinemapp.data.service

import com.manubla.cinemapp.data.service.response.ConfigurationResponse
import retrofit2.http.GET

interface ConfigurationService {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}
