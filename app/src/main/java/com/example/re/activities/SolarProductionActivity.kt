package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class SolarProductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_production)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.solar_production_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.solar_production_description)
        setupProductionCards()
    }

    private fun setupProductionCards() {
        val productionData = listOf(
            ProductionCardData(
                R.id.productionCard1,
                R.id.productionCard1Title,
                R.id.productionCard1Description,
                R.id.productionIcon1,
                R.string.annual_production_title,
                R.string.annual_production_description,
                R.drawable.ic_production
            ),
            ProductionCardData(
                R.id.productionCard2,
                R.id.productionCard2Title,
                R.id.productionCard2Description,
                R.id.productionIcon2,
                R.string.peak_production_title,
                R.string.peak_production_description,
                R.drawable.ic_peak
            ),
            ProductionCardData(
                R.id.productionCard3,
                R.id.productionCard3Title,
                R.id.productionCard3Description,
                R.id.productionIcon3,
                R.string.storage_title,
                R.string.storage_description,
                R.drawable.ic_storage
            ),
            ProductionCardData(
                R.id.productionCard4,
                R.id.productionCard4Title,
                R.id.productionCard4Description,
                R.id.productionIcon4,
                R.string.grid_integration_title,
                R.string.grid_integration_description,
                R.drawable.ic_grid
            )
        )

        productionData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }

    private data class ProductionCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val iconId: Int,
        val titleRes: Int,
        val descriptionRes: Int,
        val iconRes: Int
    )
}