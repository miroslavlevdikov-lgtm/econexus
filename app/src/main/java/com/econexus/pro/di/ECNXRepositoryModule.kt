package com.econexus.pro.di

import com.econexus.pro.data.repository.ECNXBookingRepositoryImpl
import com.econexus.pro.data.repository.ECNXServicesRepositoryImpl
import com.econexus.pro.domain.repository.ECNXBookingRepository
import com.econexus.pro.domain.repository.ECNXServicesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ECNXRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindServicesRepository(
        impl: ECNXServicesRepositoryImpl
    ): ECNXServicesRepository

    @Binds
    @Singleton
    abstract fun bindBookingRepository(
        impl: ECNXBookingRepositoryImpl
    ): ECNXBookingRepository
}