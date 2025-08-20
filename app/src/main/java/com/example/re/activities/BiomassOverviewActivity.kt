package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView
import android.widget.TextView

class BiomassOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biomass_overview)

        findViewById<TextView>(R.id.overviewTitle).text = getString(R.string.biomass_overview_title)
        findViewById<TextView>(R.id.overviewDescription).text = getString(R.string.biomass_overview_description)

        setupKeyFactsCards()
    }

    private fun setupKeyFactsCards() {
        val cards = listOf(
            CardData(
                R.id.factCard1,
                R.id.factTitle1,
                R.id.factDescription1,
                R.string.biomass_fact1_title,
                R.string.biomass_fact1_description
            ),
            CardData(
                R.id.factCard2,
                R.id.factTitle2,
                R.id.factDescription2,
                R.string.biomass_fact2_title,
                R.string.biomass_fact2_description
            ),
            CardData(
                R.id.factCard3,
                R.id.factTitle,
                R.id.factDescription,
                R.string.biomass_fact3_title,
                R.string.biomass_fact3_description
            )
        )

        cards.forEach { data ->
            findViewById<MaterialCardView>(data.cardId)?.apply {
                findViewById<TextView>(data.titleId)?.setText(data.titleRes)
                findViewById<TextView>(data.descriptionId)?.setText(data.descriptionRes)
            }
        }
    }

    private data class CardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val titleRes: Int,
        val descriptionRes: Int
    )
}