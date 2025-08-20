package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class BiomassEfficiencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biomass_efficiency)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.biomass_efficiency_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.biomass_efficiency_description)

        setupEfficiencyCards()
    }

    private fun setupEfficiencyCards() {
        val efficiencyData = listOf(
            CardData(
                R.string.biomass_conversion_efficiency_title,
                R.string.biomass_conversion_efficiency_description,
                R.drawable.ic_biomass_conversion,
                R.id.efficiencyCard1,
                R.id.cardIcon1,
                R.id.cardTitle,
                R.id.cardDescription
            ),
            CardData(
                R.string.biomass_feedstock_quality_title,
                R.string.biomass_feedstock_quality_description,
                R.drawable.ic_biomass_feedstock,
                R.id.efficiencyCard2,
                R.id.cardIcon2,
                R.id.cardTitle2,
                R.id.cardDescription2
            ),
            CardData(
                R.string.biomass_system_optimization_title,
                R.string.biomass_system_optimization_description,
                R.drawable.ic_biomass_optimization,
                R.id.efficiencyCard3,
                R.id.cardIcon3,
                R.id.cardTitle3,
                R.id.cardDescription3
            ),
            CardData(
                R.string.biomass_maintenance_title,
                R.string.biomass_maintenance_description,
                R.drawable.ic_biomass_maintenance,
                R.id.efficiencyCard4,
                R.id.cardIcon4,
                R.id.cardTitle4,
                R.id.cardDescription4
            )
        )

        efficiencyData.forEach { data ->
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
