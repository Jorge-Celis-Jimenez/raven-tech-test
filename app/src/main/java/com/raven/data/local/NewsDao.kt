package com.raven.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY createdAt DESC")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsList: List<NewsEntity>)

    @Query("DELETE FROM news WHERE objectID = :newsId")
    suspend fun deleteNews(newsId: String)

    @Query("DELETE FROM news")
    suspend fun clearAllNews()

    @Query("SELECT objectID FROM news")
    suspend fun getAllStoredIds(): List<String>
}
