package com.rubmar.goodgym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rubmar.goodgym.auth.AuthViewModel
import com.rubmar.goodgym.auth.EditProfileScreen
import com.rubmar.goodgym.auth.HomeScreen
import com.rubmar.goodgym.auth.InfoScreen
import com.rubmar.goodgym.auth.LoginScreen
import com.rubmar.goodgym.auth.ProfileSettingsScreen
import com.rubmar.goodgym.auth.RegisterScreen
import com.rubmar.goodgym.auth.SubscriptionScreen
import com.rubmar.goodgym.auth.UserListScreen
import com.rubmar.goodgym.ui.theme.GoodGymTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoodGymTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent
                ) { innerPadding ->
                    AuthNavigation()
                }
            }
        }
    }
}

@Composable
fun AuthNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("info") {
            InfoScreen(navController = navController)
        }
        composable(
            "subscription/{nombre}/{apellido}/{edad}/{email}/{password}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("apellido") { type = NavType.StringType },
                navArgument("edad") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            SubscriptionScreen(
                navController = navController,
                authViewModel = authViewModel,
                nombre = backStackEntry.arguments?.getString("nombre") ?: "",
                apellido = backStackEntry.arguments?.getString("apellido") ?: "",
                edad = backStackEntry.arguments?.getString("edad") ?: "",
                email = backStackEntry.arguments?.getString("email") ?: "",
                password = backStackEntry.arguments?.getString("password") ?: ""
            )
        }
        composable(
            "home/{userId}/{userName}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            HomeScreen(
                navController = navController,
                userId = backStackEntry.arguments?.getString("userId"),
                userName = backStackEntry.arguments?.getString("userName")
            )
        }
        composable("user_list") {
            UserListScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(
            "profile_settings/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProfileSettingsScreen(
                navController = navController,
                authViewModel = authViewModel,
                userId = backStackEntry.arguments?.getString("userId")
            )
        }
        composable(
            "edit_profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            EditProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                userId = backStackEntry.arguments?.getString("userId")
            )
        }
    }
}
