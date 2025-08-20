package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class BiomassTechnologyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biomass_technology)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.biomass_technology_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.biomass_technology_description)

        setupTechnologyCards()
    }

    private fun setupTechnologyCards() {
        val technologyData = listOf(
            CardData(
                R.string.biomass_direct_combustion_title,
                R.string.biomass_direct_combustion_description,
                R.drawable.ic_biomass_combustion
            ),
            CardData(
                R.string.biomass_gasification_title,
                R.string.biomass_gasification_description,
                R.drawable.ic_biomass_gasification
            ),
            CardData(
                R.string.biomass_anaerobic_title,
                R.string.biomass_anaerobic_description,
                R.drawable.ic_biomass_anaerobic
            ),
            CardData(
                R.string.biomass_hybrid_title,
                R.string.biomass_hybrid_description,
                R.drawable.ic_biomass_hybrid
            )
        )

        technologyData.forEachIndexed { index, data ->
            val ids = when (index) {
                0 -> IdGroup(R.id.technologyCard1, R.id.technologyCard1Title,
                    R.id.technologyCard1Description, R.id.technologyIcon1)
                1 -> IdGroup(R.id.technologyCard2, R.id.technologyCard2Title,
                    R.id.technologyCard2Description, R.id.technologyIcon2)
                2 -> IdGroup(R.id.technologyCard3, R.id.technologyCard3Title,
                    R.id.technologyCard3Description, R.id.technologyIcon3)
                else -> IdGroup(R.id.technologyCard4, R.id.technologyCard4Title,
                    R.id.technologyCard4Description, R.id.technologyIcon4)
            }

            findViewById<MaterialCardView>(ids.card).apply {
                findViewById<TextView>(ids.title).setText(data.titleRes)
                findViewById<TextView>(ids.description).setText(data.descRes)
                findViewById<ImageView>(ids.icon).setImageResource(data.iconRes)
            }
        }
    }

    private data class CardData(
        val titleRes: Int,
        val descRes: Int,
        val iconRes: Int
    )

    private data class IdGroup(
        val card: Int,
        val title: Int,
        val description: Int,
        val icon: Int
    )
}