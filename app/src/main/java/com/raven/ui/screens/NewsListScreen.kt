package com.raven.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.raven.data.local.NewsEntity
import com.raven.model.formatTimeAgo
import com.raven.ui.navigation.Screen
import com.raven.viewmodel.NewsViewModel

@Composable
fun NewsListScreen(
    navController: NavController,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    val newsList by viewModel.newsList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    // TODO: Migrate to Modifier.pullRefresh aprroach which oddly is not working.
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refreshNews() },
    ) {
        LazyColumn {
            items(newsList) { newsItem ->
                NewsItemRow(
                    newsItem = newsItem,
                    onItemClick = {
                        newsItem.storyUrl?.let {
                            navController.navigate(Screen.NewsDetail.createRoute(it))
                        }
                    },
                    onDeleteClick = {
                        viewModel.deleteNews(newsItem)
                    },
                )
            }
        }
    }
}

@Composable
fun NewsItemRow(
    newsItem: NewsEntity,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .clickable { onItemClick() },
            ) {
                Text(text = newsItem.storyTitle ?: "No title", fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.padding(2.dp)) {
                    Text(text = newsItem.author ?: "Unknown", fontSize = 12.sp)
                    Text(text = " - ", fontSize = 12.sp)
                    Text(text = newsItem.formatTimeAgo(), fontSize = 12.sp)
                }
            }

            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}
