package com.manubla.freemarket.data.repository.base

import com.manubla.freemarket.data.model.base.Model

interface SourceRepository {
    suspend fun fetchData(query: String, page: Int, pageSize: Int): List<Model>?
}