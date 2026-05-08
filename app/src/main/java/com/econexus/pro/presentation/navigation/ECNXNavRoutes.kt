package com.econexus.pro.presentation.navigation

object ECNXNavRoutes {
    const val HOME = "home"
    const val SERVICES = "services"
    const val SERVICE_DETAILS = "service_details/{serviceId}"
    const val PORTFOLIO = "portfolio"
    const val CASE_DETAILS = "case_details/{caseId}"
    const val ARTICLES = "articles"
    const val ARTICLE_DETAILS = "article_details/{articleId}"
    const val BOOKING = "booking?serviceTitle={serviceTitle}"
    const val CONFIRMATION = "confirmation/{bookingId}"
    const val SETTINGS = "settings"
    const val BOOKINGS_HISTORY = "bookings_history"
    const val BOOKING_DETAILS = "booking_details/{bookingId}"
    const val ONBOARDING = "onboarding"

    fun serviceDetails(serviceId: Int) = "service_details/$serviceId"
    fun caseDetails(caseId: Int) = "case_details/$caseId"
    fun articleDetails(articleId: Int) = "article_details/$articleId"
    fun booking(serviceTitle: String? = null) =
        if (serviceTitle != null) "booking?serviceTitle=$serviceTitle" else "booking"
    fun confirmation(bookingId: Long) = "confirmation/$bookingId"
    fun bookingDetails(bookingId: Long) = "booking_details/$bookingId"
}