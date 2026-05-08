package com.econexus.pro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.econexus.pro.domain.model.ECNXBooking

@Database(entities = [ECNXBooking::class], version = 1, exportSchema = false)
abstract class ECNXAppDatabase : RoomDatabase() {
    abstract fun bookingDao(): ECNXBookingDao
}
