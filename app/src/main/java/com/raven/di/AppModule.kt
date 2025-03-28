package com.raven.di

import com.raven.data.api.HackerNewsApi
import com.raven.data.local.NewsDao
import com.raven.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsRepository(
        newsDao: NewsDao,
        hackerNewsApi: HackerNewsApi,
    ): NewsRepository {
        return NewsRepository(newsDao = newsDao, api = hackerNewsApi)
    }
}
