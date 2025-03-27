package com.raven.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raven.model.NewsItem
import com.raven.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
    @Inject
    constructor(
        private val repository: NewsRepository,
    ) : ViewModel() {
        private val _newsList = MutableStateFlow<List<NewsItem>>(emptyList())
        val newsList: StateFlow<List<NewsItem>> = _newsList

        private val _isRefreshing = MutableStateFlow(false)
        val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

        init {
            fetchNews()
        }

        private fun fetchNews() {
            viewModelScope.launch {
                try {
                    _newsList.value = repository.fetchNews().hits
                } catch (e: Exception) {
                    Log.e("NewsViewModel", "Error fetching news", e)
                }
            }
        }

        fun refreshNews() {
            viewModelScope.launch {
                _isRefreshing.value = true
                try {
                    _newsList.value = repository.fetchNews().hits
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }
