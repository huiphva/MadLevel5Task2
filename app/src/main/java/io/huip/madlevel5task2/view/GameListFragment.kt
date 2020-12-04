package io.huip.madlevel5task2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.huip.madlevel5task2.R
import io.huip.madlevel5task2.model.Game
import io.huip.madlevel5task2.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_list.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameListFragment : Fragment() {
    private var games: ArrayList<Game> = arrayListOf()

    private lateinit var gamesAdapter: GamesAdapter
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesAdapter = GamesAdapter(games)
        rvGames.adapter = gamesAdapter
        rvGames.layoutManager = LinearLayoutManager(activity)

        observeGames()
    }


    private fun observeGames() {
        //TODO: understand this code
        viewModel.games.observe(viewLifecycleOwner) { logs: List<Game> ->
            this@GameListFragment.games.clear()
            this@GameListFragment.games.addAll(logs)
            gamesAdapter.notifyDataSetChanged()
        }

        viewModel.success.observe(viewLifecycleOwner) {
            Snackbar.make(View(context), "Success", Snackbar.LENGTH_LONG)
        }
    }

}