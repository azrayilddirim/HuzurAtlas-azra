package com.acm431.huzuratlasi.ui.theme

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.acm431.huzuratlasi.R
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.acm431.huzuratlasi.ui.theme.MedicineScreen
import com.acm431.huzuratlasi.ui.theme.NewsScreen
import com.acm431.huzuratlasi.ui.theme.MapScreen
import com.acm431.huzuratlasi.ui.theme.ProfileScreen
import com.acm431.huzuratlasi.ui.theme.BottomNavigationBar
import com.acm431.huzuratlasi.ui.theme.Onboarding1
import com.acm431.huzuratlasi.ui.theme.Onboarding2
import com.acm431.huzuratlasi.ui.theme.EmergencyScreen
import com.acm431.huzuratlasi.ui.theme.LoginPage
import com.acm431.huzuratlasi.ui.theme.RegisterScreen
import com.acm431.huzuratlasi.data.AppDatabase
import com.acm431.huzuratlasi.repository.AppRepository
import com.acm431.huzuratlasi.viewmodel.AppViewModel
import com.acm431.huzuratlasi.viewmodel.AppViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SharedPreferences'i başlatıyoruz
        sharedPreferences = getSharedPreferences("OnboardingPrefs", MODE_PRIVATE)

        // Onboarding durumu kontrolü
        val isOnboardingSeen = sharedPreferences.getBoolean("isOnboardingSeen", false)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = AppRepository(database.userDao(), database.medicineDao())
        appViewModel = ViewModelProvider(
            this,
            AppViewModelFactory(repository)
        )[AppViewModel::class.java]

        // Ana ekranı Compose ile set ediyoruz
        setContent {
            MaterialTheme {
                MainScreen(isOnboardingSeen, appViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreen(navController: NavHostController, viewModel: AppViewModel) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = currentRoute)
        }
    ) { paddingValues ->
        MedicineScreenContent(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(isOnboardingSeen: Boolean, appViewModel: AppViewModel) {
    val navController = rememberAnimatedNavController()
    
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedNavHost(
            navController = navController,
            startDestination = if (isOnboardingSeen) "login" else "onboarding1"
        ) {
            // Onboarding screens
            composable(
                route = "onboarding1",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { Onboarding1(navController) }
            
            composable(
                route = "onboarding2",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { Onboarding2(navController) }

            // Auth screens
            composable(
                route = "login",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { LoginPage(navController, appViewModel) }

            composable(
                route = "register",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { RegisterScreen(navController, appViewModel) }

            // Main app screens
            composable(
                route = "home",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { HomeScreen(navController, appViewModel) }

            composable(
                route = "medicine",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { MedicineScreen(navController, appViewModel) }

            composable(
                route = "emergency",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { EmergencyScreen(navController) }

            composable(
                route = "news",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { NewsScreen(navController) }

            composable(
                route = "profile",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
            ) { ProfileScreen(navController, appViewModel) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Medicine,
            BottomNavItem.Emergency,
            BottomNavItem.News,
            BottomNavItem.Profile
        )

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


