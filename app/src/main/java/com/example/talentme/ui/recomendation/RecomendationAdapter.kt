package com.example.talentme.ui.recomendation
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talentme.R

class RecomendationAdapter(private val recomendationList: List<Recomendation>) : RecyclerView.Adapter<RecomendationAdapter.RecomendationViewHolder>() {

    class RecomendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val major: TextView = itemView.findViewById(R.id.major_recomendation)
        val sector: TextView = itemView.findViewById(R.id.sector)
        val university: TextView = itemView.findViewById(R.id.university)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recomendation, parent, false)
        return RecomendationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecomendationViewHolder, position: Int) {
        val recomendation = recomendationList[position]
        holder.major.text = recomendation.major
        holder.sector.text = recomendation.sector
        holder.university.text = recomendation.university
    }

    override fun getItemCount(): Int {
        return recomendationList.size
    }
}
