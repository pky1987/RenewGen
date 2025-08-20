package com.example.re

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AboutUsAdapter(private val renewableEnergyGuide: String) : RecyclerView.Adapter<AboutUsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.about_us_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(renewableEnergyGuide)
    }

    override fun getItemCount(): Int {
        return 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val guideTextView: TextView = itemView.findViewById(R.id.textView_c)

        fun bind(guideText: String) {
            guideTextView.text = guideText
            guideTextView.setTextIsSelectable(true)
        }
    }
}
