package com.econexus.pro.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class ECNXBooking(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookingNumber: String,
    val fullName: String,
    val companyName: String,
    val phone: String,
    val email: String,
    val serviceTitle: String,
    val format: String,
    val date: String,
    val time: String,
    val address: String? = null,
    val comment: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val status: String = "Confirmed"
)