package com.manubla.cinemapp.presentation.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.cinemapp.data.model.Genre
import com.manubla.cinemapp.data.repository.configuration.ConfigurationSourceRepository
import com.manubla.cinemapp.data.repository.genre.GenresSourceRepository
import com.manubla.cinemapp.data.service.response.ConfigurationResponse
import com.manubla.cinemapp.data.service.response.GenreResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel(private val genresRepository: GenresSourceRepository,
                      private val configRepository: ConfigurationSourceRepository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val success: LiveData<Boolean>
        get() = localSuccess

    private val localSuccess = MutableLiveData<Boolean>()

    fun loadData() {
        launch(Dispatchers.IO) {
            val genreResponse = getRemoteGenres()
            val configResponse = getRemoteConfiguration()

            genreResponse?.let {
                storeGenres(it.genres)
            }

            if (configResponse != null)
                storeConfiguration(configResponse)

            localSuccess.postValue(true)
        }
    }

    private suspend fun storeGenres(genres: List<Genre>) {
        try {
            genresRepository.storeGenres(genres)
        } catch (ignored: Exception) {
        }
    }

    private suspend fun getRemoteGenres(): GenreResponse? = try {
            genresRepository.getRemoteGenres()
        } catch (ignored: Exception) {
            null
        }

    private suspend fun storeConfiguration(config: ConfigurationResponse) {
        try {
            configRepository.storeConfiguration(config)
        } catch (ignored: Exception) {
        }
    }

    private suspend fun getRemoteConfiguration(): ConfigurationResponse? = try {
        configRepository.getRemoteConfiguration()
    } catch (ignored: Exception) {
        null
    }

}