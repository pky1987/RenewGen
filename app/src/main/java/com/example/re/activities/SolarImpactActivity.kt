package com.example.re.activities
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class SolarImpactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_impact)
        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.solar_impact_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.solar_impact_description)
        setupImpactCards()
    }

    private fun setupImpactCards() {
        val impactData = listOf(
            ImpactCardData(
                R.id.impactCard1,
                R.id.impactCard1Title,
                R.id.impactCard1Description,
                R.id.impactIcon1,
                R.string.environmental_impact_title,
                R.string.environmental_impact_description,
                R.drawable.ic_environment
            ),
            ImpactCardData(
                R.id.impactCard2,
                R.id.impactCard2Title,
                R.id.impactCard2Description,
                R.id.impactIcon2,
                R.string.economic_impact_title,
                R.string.economic_impact_description,
                R.drawable.ic_economic
            ),
            ImpactCardData(
                R.id.impactCard3,
                R.id.impactCard3Title,
                R.id.impactCard3Description,
                R.id.impactIcon3,
                R.string.social_impact_title,
                R.string.social_impact_description,
                R.drawable.ic_social
            ),
            ImpactCardData(
                R.id.impactCard4,
                R.id.impactCard4Title,
                R.id.impactCard4Description,
                R.id.impactIcon4,
                R.string.future_impact_title,
                R.string.future_impact_description,
                R.drawable.ic_future
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