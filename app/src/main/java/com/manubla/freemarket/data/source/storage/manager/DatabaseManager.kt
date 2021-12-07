package com.manubla.freemarket.data.source.storage.manager

interface DatabaseManager {
    suspend fun clearAllTables()
}