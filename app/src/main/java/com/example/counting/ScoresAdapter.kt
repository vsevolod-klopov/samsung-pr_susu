package com.example.counting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoresAdapter(
    private val scores: MutableList<Pair<String, Int>>
) :
    RecyclerView.Adapter<ScoresAdapter.ScoresHolder>() {

    inner class ScoresHolder(view: View) : RecyclerView.ViewHolder(view) {

        val modeTV: TextView = view.findViewById(R.id.mode)
        val scoreTV: TextView = view.findViewById(R.id.score)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return ScoresHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoresHolder, position: Int) {
        val score = scores[position]
        holder.modeTV.text = score.first
        holder.scoreTV.text = score.second.toString()
    }

    override fun getItemCount(): Int {
        return scores.size
    }
}
