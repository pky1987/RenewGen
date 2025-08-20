package com.example.re.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView
import android.widget.TextView

class WaveOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_overview)

        findViewById<TextView>(R.id.overviewTitle).text = getString(R.string.wave_overview_title)
        findViewById<TextView>(R.id.overviewDescription).text = getString(R.string.wave_overview_description)

        setupKeyFactsCards()
    }

    private fun setupKeyFactsCards() {
        val cards = listOf(
            FactCardData(
                cardId = R.id.factCard1,
                titleId = R.id.factTitle1,
                descriptionId = R.id.factDescription1,
                titleRes = R.string.wave_fact1_title,
                descriptionRes = R.string.wave_fact1_description
            ),
            FactCardData(
                cardId = R.id.factCard2,
                titleId = R.id.factTitle2,
                descriptionId = R.id.factDescription2,
                titleRes = R.string.wave_fact2_title,
                descriptionRes = R.string.wave_fact2_description
            ),
            FactCardData(
                cardId = R.id.factCard3,
                titleId = R.id.factTitle3,
                descriptionId = R.id.factDescription3,
                titleRes = R.string.wave_fact3_title,
                descriptionRes = R.string.wave_fact3_description
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