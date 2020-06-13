package com.manubla.cinemapp.data.repository.configuration

import android.content.SharedPreferences
import com.manubla.cinemapp.data.service.response.ConfigurationResponse
import com.manubla.cinemapp.data.service.response.ImagesConfigurationResponse

class ConfigurationDataStoreImplDatabase(private val sharedPreferences: SharedPreferences) : ConfigurationDataStore {

    private val imagesUrlKey = "imagesUrl"
    private val posterSizeKey = "posterSize"

    override suspend fun getConfiguration(): ConfigurationResponse? {
        val imagesUrl = sharedPreferences.getString(imagesUrlKey, "")
        val posterSize = sharedPreferences.getString(posterSizeKey, "")

        return if (imagesUrl.isNullOrEmpty() || posterSize.isNullOrEmpty())
            null
        else
            ConfigurationResponse(
                ImagesConfigurationResponse(imagesUrl, imagesUrl, listOf(), listOf(posterSize)))
    }

    suspend fun storeConfiguration(config: ConfigurationResponse) {
        sharedPreferences.edit()
            .putString(imagesUrlKey, config.images.secureBaseUrl)
            .putString(posterSizeKey, config.images.posterSizes[config.images.posterSizes.size / 2])
            .apply()
    }

}