package com.example.re.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class GeothermalEfficiencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geothermal_efficiency)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.geothermal_efficiency_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.geothermal_efficiency_description)

        setupEfficiencyCards()
    }

    private fun setupEfficiencyCards() {
        val efficiencyData = listOf(
            EfficiencyCardData(
                cardId = R.id.efficiencyCard1,
                titleId = R.id.efficiencyCard1Title,
                descId = R.id.efficiencyCard1Description,
                titleRes = R.string.geothermal_plant_efficiency_title,
                descRes = R.string.geothermal_plant_efficiency_description
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard2,
                titleId = R.id.efficiencyCard2Title,
                descId = R.id.efficiencyCard2Description,
                titleRes = R.string.geothermal_temperature_title,
                descRes = R.string.geothermal_temperature_description
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard3,
                titleId = R.id.efficiencyCard3Title,
                descId = R.id.efficiencyCard3Description,
                titleRes = R.string.geothermal_well_placement_title,
                descRes = R.string.geothermal_well_placement_description
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard4,
                titleId = R.id.efficiencyCard4Title,
                descId = R.id.efficiencyCard4Description,
                titleRes = R.string.geothermal_maintenance_title,
                descRes = R.string.geothermal_maintenance_description
            )
        )

        efficiencyData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<TextView>(data.titleId).setText(data.titleRes)
                findViewById<TextView>(data.descId).setText(data.descRes)
            }
        }
    }

    private data class EfficiencyCardData(
        val cardId: Int,
        val titleId: Int,
        val descId: Int,
        val titleRes: Int,
        val descRes: Int
    )
}