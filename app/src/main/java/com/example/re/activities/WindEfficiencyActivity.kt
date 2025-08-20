package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WindEfficiencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wind_efficiency)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wind_efficiency_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wind_efficiency_description)

        setupEfficiencyCards()
    }

    private fun setupEfficiencyCards() {
        val efficiencyData = listOf(
            EfficiencyCardData(
                cardId = R.id.efficiencyCard1,
                titleId = R.id.efficiencyCardTitle1,
                descriptionId = R.id.efficiencyCardDescription1,
                iconId = R.id.efficiencyIcon1,
                titleRes = R.string.turbine_efficiency_title,
                descriptionRes = R.string.turbine_efficiency_description,
                iconRes = R.drawable.ic_turbine_efficiency
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard2,
                titleId = R.id.efficiencyCardTitle2,
                descriptionId = R.id.efficiencyCardDescription2,
                iconId = R.id.efficiencyIcon2,
                titleRes = R.string.wind_speed_title,
                descriptionRes = R.string.wind_speed_description,
                iconRes = R.drawable.ic_wind_speed
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard3,
                titleId = R.id.efficiencyCardTitle3,
                descriptionId = R.id.efficiencyCardDescription3,
                iconId = R.id.efficiencyIcon3,
                titleRes = R.string.turbine_placement_title,
                descriptionRes = R.string.turbine_placement_description,
                iconRes = R.drawable.ic_turbine_placement
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard4,
                titleId = R.id.efficiencyCardTitle4,
                descriptionId = R.id.efficiencyCardDescription4,
                iconId = R.id.efficiencyIcon4,
                titleRes = R.string.wind_maintenance_title,
                descriptionRes = R.string.wind_maintenance_description,
                iconRes = R.drawable.ic_wind_maintenance
            )
        )

        efficiencyData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).setText(data.titleRes)
                findViewById<TextView>(data.descriptionId).setText(data.descriptionRes)
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