package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class GeothermalProductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geothermal_production)

        findViewById<TextView>(R.id.productionTitle).text = getString(R.string.geothermal_production_title)
        findViewById<TextView>(R.id.productionDescription).text = getString(R.string.geothermal_production_description)

        setupProductionCards()
    }

    private fun setupProductionCards() {
        val productionData = listOf(
            CardData(
                R.string.geothermal_annual_production_title,
                R.string.geothermal_annual_production_description,
                R.drawable.ic_geothermal_production,
                R.id.productionCard1,
                R.id.productionIcon1,
                R.id.productionTitle1,
                R.id.productionDescription1
            ),
            CardData(
                R.string.geothermal_capacity_factor_title,
                R.string.geothermal_capacity_factor_description,
                R.drawable.ic_geothermal_capacity,
                R.id.productionCard2,
                R.id.productionIcon2,
                R.id.productionTitle2,
                R.id.productionDescription2
            ),
            CardData(
                R.string.geothermal_power_density_title,
                R.string.geothermal_power_density_description,
                R.drawable.ic_geothermal_density,
                R.id.productionCard3,
                R.id.productionIcon3,
                R.id.productionTitle3,
                R.id.productionDescription3
            ),
            CardData(
                R.string.geothermal_grid_integration_title,
                R.string.geothermal_grid_integration_description,
                R.drawable.ic_geothermal_grid,
                R.id.productionCard4,
                R.id.productionIcon4,
                R.id.productionTitle4,
                R.id.productionDescription4
            )
        )

        productionData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId)?.apply {
                findViewById<ImageView>(data.iconId)?.setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId)?.setText(data.titleRes)
                findViewById<TextView>(data.descriptionId)?.setText(data.descriptionRes)
            }
        }
    }

    private data class CardData(
        val titleRes: Int,
        val descriptionRes: Int,
        val iconRes: Int,
        val cardId: Int,
        val iconId: Int,
        val titleId: Int,
        val descriptionId: Int
    )
}
