package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class HydroImpactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hydro_impact)
        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.hydro_impact_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.hydro_impact_description)
        setupImpactCards()
    }

    private fun setupImpactCards() {
        val impactData = listOf(
            ImpactCardData(
                R.id.impactCard1,
                R.id.impactCardTitle1,
                R.id.impactCardDescription1,
                R.id.impactIcon1,
                R.string.hydro_environmental_impact_title,
                R.string.hydro_environmental_impact_description,
                R.drawable.ic_biomass_environment
            ),
            ImpactCardData(
                R.id.impactCard2,
                R.id.impactCardTitle2,
                R.id.impactCardDescription2,
                R.id.impactIcon2,
                R.string.hydro_social_impact_title,
                R.string.hydro_social_impact_description,
                R.drawable.ic_biomass_social
            ),
            ImpactCardData(
                R.id.impactCard3,
                R.id.impactCardTitle3,
                R.id.impactCardDescription3,
                R.id.impactIcon3,
                R.string.hydro_economic_impact_title,
                R.string.hydro_economic_impact_description,
                R.drawable.ic_biomass_economic
            )
        )

        impactData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }

    private data class ImpactCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val iconId: Int,
        val titleRes: Int,
        val descriptionRes: Int,
        val iconRes: Int
    )
}