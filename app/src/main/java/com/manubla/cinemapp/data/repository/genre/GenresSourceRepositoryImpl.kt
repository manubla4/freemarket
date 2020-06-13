package com.manubla.cinemapp.data.repository.genre

import com.manubla.cinemapp.data.model.Genre
import com.manubla.cinemapp.data.repository.genres.GenresDataStoreFactory
import com.manubla.cinemapp.data.service.response.GenreResponse

class GenresSourceRepositoryImpl(var factory: GenresDataStoreFactory) :
    GenresSourceRepository {

    override suspend fun getRemoteGenres(): GenreResponse {
        return factory.genresDataStoreCloud.getRemoteGenres()
    }

    override suspend fun storeGenres(genres: List<Genre>) {
        factory.genresDataStoreDatabase.storeGenres(genres)
    }

}