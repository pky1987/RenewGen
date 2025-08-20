package com.example.re

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.re.data.LocationEntity
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class LocationAdapter : ListAdapter<LocationEntity, LocationAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val tvLocationDetails: TextView = itemView.findViewById(R.id.tvLocationDetails)
        private val tvCoordinates: TextView = itemView.findViewById(R.id.tvCoordinates)

        fun bind(location: LocationEntity) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())
            tvDateTime.text = dateFormat.format(Date(location.timestamp))
            tvAddress.text = location.address
            tvLocationDetails.text = "${location.city}, ${location.state}, ${location.country}"
            tvCoordinates.text = "Lat: ${location.latitude}, Long: ${location.longitude}"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LocationEntity>() {
        override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity) = 
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LocationEntity, newItem: LocationEntity) = 
            oldItem == newItem
    }
}