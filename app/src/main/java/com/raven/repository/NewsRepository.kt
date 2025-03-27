package com.raven.repository

import com.raven.data.api.HackerNewsApi
import javax.inject.Inject

class NewsRepository
    @Inject
    constructor(private val hackersNewsApi: HackerNewsApi) {
        suspend fun fetchNews() = hackersNewsApi.getNews()
    }
