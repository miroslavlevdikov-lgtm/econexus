package com.econexus.pro.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.econexus.pro.presentation.screens.*

@Composable
fun ECNXNavGraph(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(),
    startDestination: String = ECNXNavRoutes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(contentPadding)
    ) {
        composable(ECNXNavRoutes.ONBOARDING) {
            ECNXOnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(ECNXNavRoutes.HOME) {
                        popUpTo(ECNXNavRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(ECNXNavRoutes.HOME) {
            ECNXHomeScreen(
                onServiceClick = { serviceId ->
                    navController.navigate(ECNXNavRoutes.serviceDetails(serviceId))
                },
                onCaseClick = { caseId ->
                    navController.navigate(ECNXNavRoutes.caseDetails(caseId))
                },
                onArticleClick = { articleId ->
                    navController.navigate(ECNXNavRoutes.articleDetails(articleId))
                },
                onViewAllServices = {
                    navController.navigate(ECNXNavRoutes.SERVICES) {
                        popUpTo(ECNXNavRoutes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onViewAllCases = {
                    navController.navigate(ECNXNavRoutes.PORTFOLIO) {
                        popUpTo(ECNXNavRoutes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(ECNXNavRoutes.SERVICES) {
            ECNXServicesScreen(
                onServiceClick = { serviceId ->
                    navController.navigate(ECNXNavRoutes.serviceDetails(serviceId))
                }
            )
        }

        composable(
            route = ECNXNavRoutes.SERVICE_DETAILS,
            arguments = listOf(navArgument("serviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getInt("serviceId") ?: 0
            ECNXServiceDetailsScreen(
                serviceId = serviceId,
                onBackClick = { navController.popBackStack() },
                onBookClick = { serviceTitle ->
                    navController.navigate(ECNXNavRoutes.booking(serviceTitle))
                }
            )
        }

        composable(ECNXNavRoutes.PORTFOLIO) {
            ECNXPortfolioScreen(
                onCaseClick = { caseId ->
                    navController.navigate(ECNXNavRoutes.caseDetails(caseId))
                }
            )
        }

        composable(
            route = ECNXNavRoutes.CASE_DETAILS,
            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getInt("caseId") ?: 0
            ECNXCaseDetailsScreen(
                caseId = caseId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(ECNXNavRoutes.ARTICLES) {
            ECNXArticlesScreen(
                onArticleClick = { articleId ->
                    navController.navigate(ECNXNavRoutes.articleDetails(articleId))
                }
            )
        }

        composable(
            route = ECNXNavRoutes.ARTICLE_DETAILS,
            arguments = listOf(navArgument("articleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: 0
            ECNXArticleDetailsScreen(
                articleId = articleId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = ECNXNavRoutes.BOOKING,
            arguments = listOf(
                navArgument("serviceTitle") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val serviceTitle = backStackEntry.arguments?.getString("serviceTitle")
            ECNXBookingScreen(
                initialServiceTitle = serviceTitle,
                onBackClick = { navController.popBackStack() },
                onBookingConfirmed = { bookingId ->
                    navController.navigate(ECNXNavRoutes.confirmation(bookingId)) {
                        popUpTo(ECNXNavRoutes.HOME)
                    }
                }
            )
        }

        composable(
            route = ECNXNavRoutes.CONFIRMATION,
            arguments = listOf(navArgument("bookingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getLong("bookingId") ?: 0L
            ECNXConfirmationScreen(
                bookingId = bookingId,
                onOkClick = {
                    navController.navigate(ECNXNavRoutes.HOME) {
                        popUpTo(ECNXNavRoutes.HOME) { inclusive = true }
                    }
                },
                onGoToServices = {
                    navController.navigate(ECNXNavRoutes.SERVICES) {
                        popUpTo(ECNXNavRoutes.HOME) { inclusive = true }
                    }
                },
                onBookAnother = {
                    navController.navigate(ECNXNavRoutes.booking()) {
                        popUpTo(ECNXNavRoutes.HOME)
                    }
                }
            )
        }

        composable(ECNXNavRoutes.SETTINGS) {
            ECNXSettingsScreen(
                onBookingsHistoryClick = {
                    navController.navigate(ECNXNavRoutes.BOOKINGS_HISTORY)
                }
            )
        }

        composable(ECNXNavRoutes.BOOKINGS_HISTORY) {
            ECNXBookingsScreen(
                onBackClick = { navController.popBackStack() },
                onBookingClick = { bookingId ->
                    navController.navigate(ECNXNavRoutes.bookingDetails(bookingId))
                }
            )
        }

        composable(
            route = ECNXNavRoutes.BOOKING_DETAILS,
            arguments = listOf(navArgument("bookingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getLong("bookingId") ?: 0L
            ECNXBookingDetailsScreen(
                bookingId = bookingId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}