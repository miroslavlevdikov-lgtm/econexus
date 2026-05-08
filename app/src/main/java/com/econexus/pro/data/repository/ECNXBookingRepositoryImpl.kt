package com.econexus.pro.data.repository

import com.econexus.pro.data.local.ECNXBookingDao
import com.econexus.pro.domain.model.ECNXBooking
import com.econexus.pro.domain.repository.ECNXBookingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ECNXBookingRepositoryImpl @Inject constructor(
    private val bookingDao: ECNXBookingDao
) : ECNXBookingRepository {

    override suspend fun insertBooking(booking: ECNXBooking): Long =
        bookingDao.insertBooking(booking)

    override fun getAllBookings(): Flow<List<ECNXBooking>> =
        bookingDao.getAllBookings()

    override suspend fun getBookingById(id: Long): ECNXBooking? =
        bookingDao.getBookingById(id)
}