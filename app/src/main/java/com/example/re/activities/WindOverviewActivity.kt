package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WindOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wind_overview)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wind_overview_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wind_overview_description)

        setupOverviewCards()
    }

    private fun setupOverviewCards() {
        val overviewData = listOf(
            OverviewCardData(
                cardId = R.id.overviewCard1,
                titleId = R.id.overviewCardTitle1,
                descriptionId = R.id.overviewCardDescription1,
                iconId = R.id.overviewIcon1,
                titleRes = R.string.wind_global_capacity_title,
                descriptionRes = R.string.wind_global_capacity_description,
                iconRes = R.drawable.ic_wind_capacity
            ),
            OverviewCardData(
                cardId = R.id.overviewCard2,
                titleId = R.id.overviewCardTitle2,
                descriptionId = R.id.overviewCardDescription2,
                iconId = R.id.overviewIcon2,
                titleRes = R.string.wind_energy_cost_title,
                descriptionRes = R.string.wind_energy_cost_description,
                iconRes = R.drawable.ic_wind_cost
            ),
            OverviewCardData(
                cardId = R.id.overviewCard3,
                titleId = R.id.overviewCardTitle3,
                descriptionId = R.id.overviewCardDescription3,
                iconId = R.id.overviewIcon3,
                titleRes = R.string.wind_growth_rate_title,
                descriptionRes = R.string.wind_growth_rate_description,
                iconRes = R.drawable.ic_wind_growth
            ),
            OverviewCardData(
                cardId = R.id.overviewCard4,
                titleId = R.id.overviewCardTitle4,
                descriptionId = R.id.overviewCardDescription4,
                iconId = R.id.overviewIcon4,
                titleRes = R.string.wind_market_share_title,
                descriptionRes = R.string.wind_market_share_description,
                iconRes = R.drawable.ic_wind_market
            )
        )

        overviewData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }

    private data class OverviewCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val iconId: Int,
        val titleRes: Int,
        val descriptionRes: Int,
        val iconRes: Int
    )
}