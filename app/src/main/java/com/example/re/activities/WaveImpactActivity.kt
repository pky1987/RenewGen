package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WaveImpactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_impact)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wave_impact_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wave_impact_description)

        setupImpactCards()
    }

    private fun setupImpactCards() {
        val impactData = listOf(
            ImpactCardData(
                cardId = R.id.impactCard1,
                titleId = R.id.impactCard1Title,
                descriptionId = R.id.impactCard1Description,
                iconId = R.id.impactIcon1,
                titleRes = R.string.wave_environmental_impact_title,
                descriptionRes = R.string.wave_environmental_impact_description,
                iconRes = R.drawable.ic_wave_environment
            ),
            ImpactCardData(
                cardId = R.id.impactCard2,
                titleId = R.id.impactCard2Title,
                descriptionId = R.id.impactCard2Description,
                iconId = R.id.impactIcon2,
                titleRes = R.string.wave_economic_impact_title,
                descriptionRes = R.string.wave_economic_impact_description,
                iconRes = R.drawable.ic_wave_economic
            ),
            ImpactCardData(
                cardId = R.id.impactCard3,
                titleId = R.id.impactCard3Title,
                descriptionId = R.id.impactCard3Description,
                iconId = R.id.impactIcon3,
                titleRes = R.string.wave_social_impact_title,
                descriptionRes = R.string.wave_social_impact_description,
                iconRes = R.drawable.ic_wave_social
            ),
            ImpactCardData(
                cardId = R.id.impactCard4,
                titleId = R.id.impactCard4Title,
                descriptionId = R.id.impactCard4Description,
                iconId = R.id.impactIcon4,
                titleRes = R.string.wave_future_impact_title,
                descriptionRes = R.string.wave_future_impact_description,
                iconRes = R.drawable.ic_wave_future
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