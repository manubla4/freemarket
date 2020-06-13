package com.manubla.cinemapp.data.repository.configuration

import com.manubla.cinemapp.data.service.response.ConfigurationResponse

interface ConfigurationDataStore {
    suspend fun getConfiguration(): ConfigurationResponse?
}