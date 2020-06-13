package com.manubla.cinemapp.data.repository.genre

import com.manubla.cinemapp.data.service.GenreService
import com.manubla.cinemapp.data.service.response.GenreResponse

class GenresDataStoreImplCloud(private var genreService: GenreService): GenresDataStore {

    override suspend fun getRemoteGenres(): GenreResponse {
        return genreService.getGenres()
    }

}