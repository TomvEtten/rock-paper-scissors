package hva.nl.rockpaperscissors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hva.nl.rockpaperscissors.UiUtiles.Companion.getDrawableFromChoiche
import kotlinx.android.synthetic.main.results_result_line.view.*

class ResultsAdapter(private val results: List<Result>) :
    RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.results_result_line,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return results.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(result: Result) {
            itemView.tvWinner.text = result.winner
            itemView.tvResultTimestamp.text = result.dateTime
            itemView.ivResultPlayer.setImageResource(getDrawableFromChoiche(result.playerChoice))
            itemView.ivResultComputer.setImageResource(getDrawableFromChoiche(result.computerChoice))
        }
    }
}
