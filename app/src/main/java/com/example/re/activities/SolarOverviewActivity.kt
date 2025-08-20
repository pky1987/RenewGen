package com.example.re.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class SolarOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_overview)

        findViewById<TextView>(R.id.overviewTitle).text = getString(R.string.solar_overview_title)
        findViewById<TextView>(R.id.overviewDescription).text = getString(R.string.solar_overview_description)
        setupKeyFactsCards()
    }

    private fun setupKeyFactsCards() {
        val cards = listOf(
            FactCardData(
                R.id.factCard1,
                R.id.factTitle1,
                R.id.factDescription1,
                R.string.fact1_title,
                R.string.fact1_description
            ),
            FactCardData(
                R.id.factCard2,
                R.id.factTitle2,
                R.id.factDescription2,
                R.string.fact2_title,
                R.string.fact2_description
            ),
            FactCardData(
                R.id.factCard3,
                R.id.factTitle3,
                R.id.factDescription3,
                R.string.fact3_title,
                R.string.fact3_description
            )
        )

        cards.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }

    private data class FactCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val titleRes: Int,
        val descriptionRes: Int
    )
}