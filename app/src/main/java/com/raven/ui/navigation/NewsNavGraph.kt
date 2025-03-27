package com.raven.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.raven.ui.screens.NewsDetailScreen
import com.raven.ui.screens.NewsListScreen
import com.raven.viewmodel.NewsViewModel

@Composable
fun NewsNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screen.NewsList.route,
    ) {
        composable(Screen.NewsList.route) {
            NewsListScreen(navController, viewModel)
        }
        composable(
            route = Screen.NewsDetail.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType }),
        ) { backStackEntry ->
            NewsDetailScreen(Screen.NewsDetail.extractUrl(backStackEntry))
        }
    }
}
