package com.econexus.pro.domain.repository

import com.econexus.pro.domain.model.ECNXBooking
import kotlinx.coroutines.flow.Flow

interface ECNXBookingRepository {
    suspend fun insertBooking(booking: ECNXBooking): Long
    fun getAllBookings(): Flow<List<ECNXBooking>>
    suspend fun getBookingById(id: Long): ECNXBooking?
}