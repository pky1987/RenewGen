package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class BiomassImpactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biomass_impact)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.biomass_impact_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.biomass_impact_description)

        setupImpactCards()
    }

    private fun setupImpactCards() {
        val impactData = listOf(
            CardData(
                R.string.biomass_environmental_impact_title,
                R.string.biomass_environmental_impact_description,
                R.drawable.ic_biomass_environment,
                R.id.impactCard1,
                R.id.cardIcon1,
                R.id.cardTitle,
                R.id.cardDescription
            ),
            CardData(
                R.string.biomass_economic_impact_title,
                R.string.biomass_economic_impact_description,
                R.drawable.ic_biomass_economic,
                R.id.impactCard2,
                R.id.cardIcon2,
                R.id.cardTitle2,
                R.id.cardDescription2
            ),
            CardData(
                R.string.biomass_social_impact_title,
                R.string.biomass_social_impact_description,
                R.drawable.ic_biomass_social,
                R.id.impactCard3,
                R.id.cardIcon3,
                R.id.cardTitle3,
                R.id.cardDescription3
            ),
            CardData(
                R.string.biomass_future_impact_title,
                R.string.biomass_future_impact_description,
                R.drawable.ic_biomass_future,
                R.id.impactCard4,
                R.id.cardIcon4,
                R.id.cardTitle4,
                R.id.cardDescription4
            )
        )

        impactData.forEach { data ->
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
