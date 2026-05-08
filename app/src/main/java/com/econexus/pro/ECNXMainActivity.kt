package com.econexus.pro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.econexus.pro.presentation.navigation.ECNXBottomNavBar
import com.econexus.pro.presentation.navigation.ECNXNavGraph
import com.econexus.pro.presentation.navigation.ECNXNavRoutes
import com.econexus.pro.presentation.navigation.bottomNavItems
import com.econexus.pro.ui.theme.EcowResoursesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ECNXMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcowResoursesTheme {
                val mainViewModel: ECNXMainViewModel = hiltViewModel()
                val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                val startDestination = uiState.startDestination
                if (startDestination == null) {
                    // Show a blank screen while determining start destination
                    androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize())
                    return@EcowResoursesTheme
                }

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Show bottom bar only on main tab screens
                val showBottomBar = currentRoute in bottomNavItems.map { it.route }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            ECNXBottomNavBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    ECNXNavGraph(
                        navController = navController,
                        contentPadding = innerPadding,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}