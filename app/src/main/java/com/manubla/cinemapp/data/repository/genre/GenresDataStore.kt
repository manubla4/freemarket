package com.manubla.cinemapp.data.repository.genre

import com.manubla.cinemapp.data.service.response.GenreResponse

interface GenresDataStore {
    suspend fun getRemoteGenres(): GenreResponse
}