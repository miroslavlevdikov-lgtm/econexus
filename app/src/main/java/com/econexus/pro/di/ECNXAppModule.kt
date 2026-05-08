package com.econexus.pro.di

import android.content.Context
import androidx.room.Room
import com.econexus.pro.data.local.ECNXAppDatabase
import com.econexus.pro.data.local.ECNXBookingDao
import com.econexus.pro.data.local.ECNXPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ECNXAppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ECNXAppDatabase {
        return Room.databaseBuilder(
            context,
            ECNXAppDatabase::class.java,
            "ecnx_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookingDao(database: ECNXAppDatabase): ECNXBookingDao {
        return database.bookingDao()
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(@ApplicationContext context: Context): ECNXPreferencesRepository {
        return ECNXPreferencesRepository(context)
    }
}