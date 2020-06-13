package com.manubla.cinemapp.data.repository.configuration

import com.manubla.cinemapp.data.service.response.ConfigurationResponse

interface ConfigurationSourceRepository {
    suspend fun getConfiguration(): ConfigurationResponse?
    suspend fun getRemoteConfiguration(): ConfigurationResponse?
    suspend fun storeConfiguration(config: ConfigurationResponse)
}