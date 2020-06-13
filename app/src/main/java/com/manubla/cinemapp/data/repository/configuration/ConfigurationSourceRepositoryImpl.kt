package com.manubla.cinemapp.data.repository.configuration

import com.manubla.cinemapp.data.service.response.ConfigurationResponse

class ConfigurationSourceRepositoryImpl(var factory: ConfigurationDataStoreFactory):
    ConfigurationSourceRepository {

    override suspend fun getConfiguration(): ConfigurationResponse? {
        val config = factory.configurationDataStoreDatabase.getConfiguration()
        return config ?: factory.configurationDataStoreCloud.getConfiguration()
    }

    override suspend fun getRemoteConfiguration(): ConfigurationResponse? {
        return factory.configurationDataStoreCloud.getConfiguration()
    }

    override suspend fun storeConfiguration(config: ConfigurationResponse) {
        factory.configurationDataStoreDatabase.storeConfiguration(config)
    }

}