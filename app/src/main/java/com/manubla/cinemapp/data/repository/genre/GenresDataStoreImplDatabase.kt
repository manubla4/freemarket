package com.manubla.cinemapp.data.repository.genre

import com.manubla.cinemapp.data.dao.GenreDao
import com.manubla.cinemapp.data.model.Genre
import com.manubla.cinemapp.data.service.response.GenreResponse

class GenresDataStoreImplDatabase(private val genreDao: GenreDao) : GenresDataStore {

    override suspend fun getRemoteGenres(): GenreResponse {
        return GenreResponse(genreDao.getAll())
    }

    suspend fun storeGenres(genres: List<Genre>) {
        genreDao.insertAll(genres)
    }

}