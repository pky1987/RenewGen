package com.example.re.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.re.R
import com.example.re.data.LocationEntity
import java.text.SimpleDateFormat
import java.util.*

class LocationAdapter : ListAdapter<LocationEntity, LocationAdapter.LocationViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        private val tvCoordinates: TextView = itemView.findViewById(R.id.tvCoordinates)
        private val tvLocationDetails: TextView = itemView.findViewById(R.id.tvLocationDetails)

        fun bind(location: LocationEntity) {
            tvAddress.text = location.address
            tvDateTime.text = formatDateTime(location.timestamp)
            tvCoordinates.text = String.format(
                "Lat: %.6f, Long: %.6f",
                location.latitude,
                location.longitude
            )
            tvLocationDetails.text = String.format(
                "%s, %s, %s",
                location.city,
                location.state,
                location.country
            )
        }

        private fun formatDateTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    class LocationDiffCallback : DiffUtil.ItemCallback<LocationEntity>() {
        override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
            return oldItem == newItem
        }
    }
} 