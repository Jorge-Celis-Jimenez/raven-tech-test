package com.raven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raven.ui.screens.NewsDetailScreen
import com.raven.ui.screens.NewsListScreen
import com.raven.ui.theme.RavenTechTestTheme
import com.raven.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RavenTechTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NewsApp(navController)
                }
            }
        }
    }
}

@Composable
fun NewsApp(
    navController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    NavHost(navController = navController, startDestination = Screen.NewsList.route) {
        composable(Screen.NewsList.route) {
            NewsListScreen(navController, viewModel)
        }
        composable(Screen.NewsDetail.route) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")
            url?.let { NewsDetailScreen(it) }
        }
    }
}

sealed class Screen(val route: String) {
    object NewsList : Screen("news_list")

    object NewsDetail : Screen("news_detail/{url}") {
        fun createRoute(url: String) = "news_detail/$url"
    }
}
