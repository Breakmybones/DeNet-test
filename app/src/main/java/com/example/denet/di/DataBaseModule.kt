package com.example.denet.di

import android.content.Context
import androidx.room.Room
import com.example.denet.data.dao.NodeDao
import com.example.denet.data.database.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideNodeDao(database: AppDataBase): NodeDao {
        return database.getNodeDao()
    }

    companion object {
        private const val DATABASE_NAME = "denet.db"
    }
}