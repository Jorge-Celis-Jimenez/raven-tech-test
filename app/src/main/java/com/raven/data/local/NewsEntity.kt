package com.raven.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val objectID: String,
    val author: String?,
    val storyTitle: String?,
    val createdAt: String?,
    val storyUrl: String?,
)
