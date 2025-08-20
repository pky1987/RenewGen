package com.example.re

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Horizontal1Adapter(private val products: List<Energy>) : RecyclerView.Adapter<Horizontal1Adapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_horizontal_1, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.name
        holder.productPrice.text = product.price
        holder.productImage.setImageResource(product.imageRes)

        if (product.imageRes == R.drawable.solarenergy) {
            holder.productImage.setOnClickListener {
                val intent = Intent(holder.itemView.context, Order::class.java).apply {
                    putExtra("ENERGY_ITEM", product)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
        else if(product.imageRes==R.drawable.windenergy) {
            holder.productImage.setOnClickListener {
                val intent = Intent(holder.itemView.context, Order::class.java).apply {
                    putExtra("ENERGY_ITEM", product)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
        else if(product.imageRes==R.drawable.hydropower) {
            holder.productImage.setOnClickListener {
                val intent = Intent(holder.itemView.context, Order::class.java).apply {
                    putExtra("ENERGY_ITEM", product)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
        else if(product.imageRes==R.drawable.geothermal) {
            holder.productImage.setOnClickListener {
                val intent = Intent(holder.itemView.context, Order::class.java).apply {
                    putExtra("ENERGY_ITEM", product)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
        else if(product.imageRes==R.drawable.biomass) {
            holder.productImage.setOnClickListener {
                val intent = Intent(holder.itemView.context, Order::class.java).apply {
                    putExtra("ENERGY_ITEM", product)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
        else if(product.imageRes==R.drawable.waveenergy) {
            holder.productImage.setOnClickListener {
                val intent = Intent(holder.itemView.context, Order::class.java).apply {
                    putExtra("ENERGY_ITEM", product)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
        else {
            holder.productImage.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.Image)
        val productName: TextView = view.findViewById(R.id.Name)
        val productPrice: TextView = view.findViewById(R.id.rupee)
    }
}