package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WaveProductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_production)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wave_production_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wave_production_description)

        setupProductionCards()
    }

    private fun setupProductionCards() {
        val productionData = listOf(
            ProductionCardData(
                cardId = R.id.productionCard1,
                titleId = R.id.productionCard1Title,
                descriptionId = R.id.productionCard1Description,
                iconId = R.id.productionIcon1,
                titleRes = R.string.wave_annual_production_title,
                descriptionRes = R.string.wave_annual_production_description,
                iconRes = R.drawable.ic_wave_production
            ),
            ProductionCardData(
                cardId = R.id.productionCard2,
                titleId = R.id.productionCard2Title,
                descriptionId = R.id.productionCard2Description,
                iconId = R.id.productionIcon2,
                titleRes = R.string.wave_capacity_factor_title,
                descriptionRes = R.string.wave_capacity_factor_description,
                iconRes = R.drawable.ic_wave_capacity
            ),
            ProductionCardData(
                cardId = R.id.productionCard3,
                titleId = R.id.productionCard3Title,
                descriptionId = R.id.productionCard3Description,
                iconId = R.id.productionIcon3,
                titleRes = R.string.wave_power_density_title,
                descriptionRes = R.string.wave_power_density_description,
                iconRes = R.drawable.ic_wave_density
            ),
            ProductionCardData(
                cardId = R.id.productionCard4,
                titleId = R.id.productionCard4Title,
                descriptionId = R.id.productionCard4Description,
                iconId = R.id.productionIcon4,
                titleRes = R.string.wave_grid_integration_title,
                descriptionRes = R.string.wave_grid_integration_description,
                iconRes = R.drawable.ic_wave_grid
            )
        )

        productionData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).setText(data.titleRes)
                findViewById<TextView>(data.descriptionId).setText(data.descriptionRes)
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