package io.huip.madlevel5task2.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.huip.madlevel5task2.R
import io.huip.madlevel5task2.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_game.*
import java.util.*

/*
 * TODO: add swipe to remove card
 * TODO: sort games by date
 */
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // TODO: figure out how this works behind the scenes
        // https://stackoverflow.com/questions/18010072/menu-items-are-not-showing-on-action-bar
        menuInflater.inflate(R.menu.menu_main, menu)
        val deleteMenuItem = menu.findItem(R.id.btnRemoveAll)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.GameListFragment -> {
                    fabActionScreenButton.setOnClickListener {
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        supportActionBar?.setDisplayShowHomeEnabled(true)
                        supportActionBar?.setTitle(R.string.addGameTitle)
                        navController.navigate(R.id.action_GameListFragment_to_AddGameFragment)
                    }

                    fabActionScreenButton.setImageResource(android.R.drawable.ic_menu_edit)
                    // Shows correct menu icon
                    deleteMenuItem.isVisible = true

                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    supportActionBar?.setDisplayShowHomeEnabled(false)
                    supportActionBar?.setTitle(R.string.app_name)
                }
                R.id.AddGameFragment -> {
                    // Shows correct menu icon
                    deleteMenuItem.isVisible = false
                    val date = Calendar.getInstance()

                    fabActionScreenButton.setOnClickListener {
                        //TODO: fix this
                        date.set(Calendar.YEAR, txtAddYear.text.toString().toInt())
                        date.set(Calendar.MONTH, txtAddMonth.text.toString().toInt())
                        date.set(Calendar.DAY_OF_MONTH, txtAddDay.text.toString().toInt())

                        viewModel.insertGame(
                                txtAddTitle.text.toString(),
                                txtAddPlatform.text.toString(),
                                Date.from(date.toInstant())
                        )

                        navController.navigate(R.id.action_AddGameFragment_to_GameListFragment)
                    }
                    fabActionScreenButton.setImageResource(android.R.drawable.ic_menu_save)
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnRemoveAll -> {
                viewModel.deleteGame()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}