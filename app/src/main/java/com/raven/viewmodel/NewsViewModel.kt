package com.raven.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raven.data.local.NewsEntity
import com.raven.model.NewsItem
import com.raven.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
    @Inject
    constructor(
        private val repository: NewsRepository,
    ) : ViewModel() {
        private val _newsList = MutableStateFlow<List<NewsItem>>(emptyList())
        val newsList = repository.getNews().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

        private val _isRefreshing = MutableStateFlow(false)
        val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

        init {
            refreshNews()
        }

        fun refreshNews() {
            viewModelScope.launch {
                _isRefreshing.value = true
                repository.refreshNews()
                _isRefreshing.value = false
            }
        }

        fun deleteNews(newsItem: NewsEntity) {
            viewModelScope.launch {
                repository.deleteNews(newsItem.objectID)
            }
        }
    }
