package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WindImpactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wind_impact)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wind_impact_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wind_impact_description)

        setupImpactCards()
    }

    private fun setupImpactCards() {
        val impactData = listOf(
            ImpactCardData(
                cardId = R.id.impactCard1,
                titleId = R.id.impactCardTitle1,
                descriptionId = R.id.impactCardDescription1,
                iconId = R.id.impactIcon1,
                titleRes = R.string.wind_environmental_impact_title,
                descriptionRes = R.string.wind_environmental_impact_description,
                iconRes = R.drawable.ic_wind_environment
            ),
            ImpactCardData(
                cardId = R.id.impactCard2,
                titleId = R.id.impactCardTitle2,
                descriptionId = R.id.impactCardDescription2,
                iconId = R.id.impactIcon2,
                titleRes = R.string.wind_economic_impact_title,
                descriptionRes = R.string.wind_economic_impact_description,
                iconRes = R.drawable.ic_wind_economic
            ),
            ImpactCardData(
                cardId = R.id.impactCard3,
                titleId = R.id.impactCardTitle3,
                descriptionId = R.id.impactCardDescription3,
                iconId = R.id.impactIcon3,
                titleRes = R.string.wind_social_impact_title,
                descriptionRes = R.string.wind_social_impact_description,
                iconRes = R.drawable.ic_wind_social
            ),
            ImpactCardData(
                cardId = R.id.impactCard4,
                titleId = R.id.impactCardTitle4,
                descriptionId = R.id.impactCardDescription4,
                iconId = R.id.impactIcon4,
                titleRes = R.string.wind_future_impact_title,
                descriptionRes = R.string.wind_future_impact_description,
                iconRes = R.drawable.ic_wind_future
            )
        )

        impactData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).setText(data.titleRes)
                findViewById<TextView>(data.descriptionId).setText(data.descriptionRes)
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