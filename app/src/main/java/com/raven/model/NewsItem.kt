package com.raven.model

import com.raven.data.local.NewsEntity
import java.time.Duration
import java.time.Instant

data class NewsItem(
    val objectID: String,
    val author: String?,
    val title: String?,
    val story_title: String?,
    val created_at: String,
    val url: String?,
    val story_url: String?,
    val _tags: List<String>?,
)

fun NewsEntity.formatTimeAgo(): String {
    val minutesAgo = this.createdAt?.toMinutesAgo() ?: return "Just now"
    return when {
        minutesAgo < 1 -> "Now"
        minutesAgo < 60 -> "${minutesAgo}m ago"
        minutesAgo < 1440 -> "${minutesAgo / 60}h ago"
        else -> "${minutesAgo / 1440}d ago"
    }
}

// Reusable for any ISO 8601 string
fun String?.toMinutesAgo(): Long? {
    return try {
        this?.let {
            Duration.between(Instant.parse(it), Instant.now()).toMinutes()
        }
    } catch (e: Exception) {
        null
    }
}
