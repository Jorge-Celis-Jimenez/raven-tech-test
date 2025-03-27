package com.raven.data.api

import com.raven.model.NewsItem
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsApi {
    @GET("search_by_date")
    suspend fun getNews(
        @Query("query") query: String = "android",
    ): NewsResponse // Define your data model
}

data class NewsResponse(
    val hits: List<NewsItem>,
)
