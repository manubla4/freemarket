package com.manubla.cinemapp.data.repository.configuration

import com.manubla.cinemapp.data.service.ConfigurationService
import com.manubla.cinemapp.data.service.response.ConfigurationResponse

class ConfigurationDataStoreImplCloud(private var configurationService: ConfigurationService) : ConfigurationDataStore {

    override suspend fun getConfiguration(): ConfigurationResponse? {
        return configurationService.getConfiguration()
    }
}