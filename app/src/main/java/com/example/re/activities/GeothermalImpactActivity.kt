package com.example.re.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class GeothermalImpactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geothermal_impact)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.geothermal_impact_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.geothermal_impact_description)

        setupImpactCards()
    }

    private fun setupImpactCards() {
        val impactData = listOf(
            ImpactCardData(
                cardId = R.id.impactCard1,
                titleId = R.id.geothermalCardTitle1,
                descId = R.id.geothermalCardDescription1,
                titleRes = R.string.geothermal_environmental_impact_title,
                descRes = R.string.geothermal_environmental_impact_description
            ),
            ImpactCardData(
                cardId = R.id.impactCard2,
                titleId = R.id.geothermalCardTitle2,
                descId = R.id.geothermalCardDescription2,
                titleRes = R.string.geothermal_economic_impact_title,
                descRes = R.string.geothermal_economic_impact_description
            ),
            ImpactCardData(
                cardId = R.id.impactCard3,
                titleId = R.id.geothermalCardTitle3,
                descId = R.id.geothermalCardDescription3,
                titleRes = R.string.geothermal_social_impact_title,
                descRes = R.string.geothermal_social_impact_description
            ),
            ImpactCardData(
                cardId = R.id.impactCard4,
                titleId = R.id.geothermalCardTitle4,
                descId = R.id.geothermalCardDescription4,
                titleRes = R.string.geothermal_future_impact_title,
                descRes = R.string.geothermal_future_impact_description
            )
        )

        impactData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<TextView>(data.titleId).setText(data.titleRes)
                findViewById<TextView>(data.descId).setText(data.descRes)
            }
        }
    }

    private data class ImpactCardData(
        val cardId: Int,
        val titleId: Int,
        val descId: Int,
        val titleRes: Int,
        val descRes: Int
    )
}