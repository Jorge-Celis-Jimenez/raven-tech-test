package com.raven.repository

import com.raven.data.api.HackerNewsApi
import com.raven.data.local.NewsDao
import com.raven.data.local.NewsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository
    @Inject
    constructor(
        private val newsDao: NewsDao,
        private val api: HackerNewsApi,
    ) {
        fun getNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()

        suspend fun refreshNews() {
            val response = api.getNews("android").hits
            val newsEntities =
                response.map {
                    NewsEntity(
                        objectID = it.objectID,
                        author = it.author,
                        storyTitle = it.story_title ?: it.title,
                        createdAt = it.created_at,
                        storyUrl = it.story_url ?: it.url,
                    )
                }

            val storedIds = newsDao.getAllStoredIds().toSet()
            val newIds = newsEntities.map { it.objectID }.toSet()
            val deletedIds = storedIds - newIds

            newsDao.insertNews(newsEntities)
            deletedIds.forEach { newsDao.deleteNews(it) }
        }

        suspend fun deleteNews(newsId: String) {
            newsDao.deleteNews(newsId)
        }
    }
