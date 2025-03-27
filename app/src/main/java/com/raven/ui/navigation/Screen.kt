package com.raven.ui.navigation

import androidx.navigation.NavBackStackEntry
import java.net.URLDecoder
import java.net.URLEncoder

sealed class Screen(val route: String) {
    object NewsList : Screen("news_list")

    object NewsDetail : Screen("news_detail/{url}") {
        fun createRoute(url: String): String {
            val encoded = URLEncoder.encode(url, "UTF-8")
            return "news_detail/$encoded"
        }

        fun extractUrl(backStackEntry: NavBackStackEntry): String {
            return URLDecoder.decode(
                backStackEntry.arguments?.getString("url") ?: "",
                "UTF-8",
            )
        }
    }
}
