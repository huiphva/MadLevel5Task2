package io.huip.madlevel5task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.huip.madlevel5task2.database.GameRepository
import io.huip.madlevel5task2.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val gameRepository = GameRepository(application.applicationContext)

    val games: LiveData<List<Game>> = gameRepository.getAllGames()
    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun insertGame(title: String, platform: String, releaseDate: Date) {
        val game = Game(title = title, platform = platform, releaseDate = releaseDate)

        if (isGame(game)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    gameRepository.insertGame(game)
                }
                success.value = true
            }
        }
    }

    fun deleteGame() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteGames()
            }
        }
    }

    private fun isGame(game: Game): Boolean {
        return when {
            game.title.isNullOrBlank() -> {
                error.value = "Enter a title!"
                false
            }
            game.platform.isNullOrBlank() -> {
                error.value = "Enter a platform!"
                false
            }
            game.releaseDate.toString().isNullOrBlank() -> {
                error.value = "Enter a releasedate!"
                false
            }
            else -> true
        }
    }
}