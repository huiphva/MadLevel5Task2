package io.huip.madlevel5task2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.huip.madlevel5task2.R
import io.huip.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.item_game.view.*
import java.text.DateFormatSymbols
import java.util.*

class GamesAdapter(private val games: List<Game>) :
        RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(game: Game) {
            val date: Calendar = Calendar.getInstance();

            // Gets date from game
            date.time = game.releaseDate

            // Fills in the data
            itemView.txtViewGameTitleBacklog.text = game.title
            itemView.txtViewPlatformTitleBacklog.text = game.platform
            itemView.txtViewReleaseDateBacklog.text =
                    String.format(
                            "Release: %s %s %s",
                            date.get(Calendar.DAY_OF_MONTH),
                            DateFormatSymbols().months[date.get(Calendar.MONTH)],
                            date.get(Calendar.YEAR)
                    )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}