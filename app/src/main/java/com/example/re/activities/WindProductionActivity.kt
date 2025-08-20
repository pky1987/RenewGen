package com.example.re.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WindProductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wind_production)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wind_production_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wind_production_description)

        setupProductionCards()
    }

    private fun setupProductionCards() {
        val productionData = listOf(
            ProductionCardData(
                cardId = R.id.productionCard1,
                titleId = R.id.productionCard1Title,
                descriptionId = R.id.productionCard1Description,
                titleRes = R.string.wind_annual_production_title,
                descriptionRes = R.string.wind_annual_production_description
            ),
            ProductionCardData(
                cardId = R.id.productionCard2,
                titleId = R.id.productionCard2Title,
                descriptionId = R.id.productionCard2Description,
                titleRes = R.string.wind_capacity_factor_title,
                descriptionRes = R.string.wind_capacity_factor_description
            ),
            ProductionCardData(
                cardId = R.id.productionCard3,
                titleId = R.id.productionCard3Title,
                descriptionId = R.id.productionCard3Description,
                titleRes = R.string.wind_power_density_title,
                descriptionRes = R.string.wind_power_density_description
            ),
            ProductionCardData(
                cardId = R.id.productionCard4,
                titleId = R.id.productionCard4Title,
                descriptionId = R.id.productionCard4Description,
                titleRes = R.string.wind_grid_integration_title,
                descriptionRes = R.string.wind_grid_integration_description
            )
        )

        productionData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }

    private data class ProductionCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val titleRes: Int,
        val descriptionRes: Int
    )
}