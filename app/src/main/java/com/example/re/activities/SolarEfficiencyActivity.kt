package com.example.re.activities
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView
class SolarEfficiencyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_efficiency)
        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.solar_efficiency_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.solar_efficiency_description)
        setupEfficiencyCards()
    }
    private fun setupEfficiencyCards() {
        val efficiencyData = listOf(
            EfficiencyCardData(
                R.id.efficiencyCard1,
                R.id.efficiencyCard1Title,
                R.id.efficiencyCard1Description,
                R.id.efficiencyIcon1,
                R.string.panel_efficiency_title,
                R.string.panel_efficiency_description,
                R.drawable.ic_panel_efficiency
            ),
            EfficiencyCardData(
                R.id.efficiencyCard2,
                R.id.efficiencyCard2Title,
                R.id.efficiencyCard2Description,
                R.id.efficiencyIcon2,
                R.string.conversion_rate_title,
                R.string.conversion_rate_description,
                R.drawable.ic_conversion
            ),
            EfficiencyCardData(
                R.id.efficiencyCard3,
                R.id.efficiencyCard3Title,
                R.id.efficiencyCard3Description,
                R.id.efficiencyIcon3,
                R.string.temperature_impact_title,
                R.string.temperature_impact_description,
                R.drawable.ic_temperature
            ),
            EfficiencyCardData(
                R.id.efficiencyCard4,
                R.id.efficiencyCard4Title,
                R.id.efficiencyCard4Description,
                R.id.efficiencyIcon4,
                R.string.maintenance_title,
                R.string.maintenance_description,
                R.drawable.ic_maintenance
            )
        )
        efficiencyData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }
    private data class EfficiencyCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val iconId: Int,
        val titleRes: Int,
        val descriptionRes: Int,
        val iconRes: Int
    )
}