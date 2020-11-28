package io.huip.madlevel5task2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.huip.madlevel5task2.model.Game

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM gameTable")
    fun getAllGames(): LiveData<List<Game>>

    @Delete
    suspend fun deleteGame(game: Game)
}