package com.raven.model

import java.time.Duration
import java.time.Instant

data class NewsItem(
    val author: String?,
    val story_title: String?,
    val created_at: String?,
    val story_url: String?,
)

fun NewsItem.formatTimeAgo(): String {
    val minutesAgo = this.created_at?.toMinutesAgo() ?: return "Just now"
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
