package com.manubla.freemarket.data.source.storage.builder

import android.content.Context
import androidx.room.Room
import com.manubla.freemarket.data.source.storage.AppDatabase

fun initRoomDatabase(context: Context): AppDatabase{
    return Room.databaseBuilder<AppDatabase>(
        context.applicationContext,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration()
    .build()
}