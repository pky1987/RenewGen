package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WaveEfficiencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_efficiency)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wave_efficiency_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wave_efficiency_description)

        setupEfficiencyCards()
    }

    private fun setupEfficiencyCards() {
        val efficiencyData = listOf(
            EfficiencyCardData(
                cardId = R.id.efficiencyCard1,
                titleId = R.id.efficiencyCard1Title,
                descriptionId = R.id.efficiencyCard1Description,
                iconId = R.id.efficiencyIcon1,
                titleRes = R.string.wave_converter_efficiency_title,
                descriptionRes = R.string.wave_converter_efficiency_description,
                iconRes = R.drawable.ic_wave_converter
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard2,
                titleId = R.id.efficiencyCard2Title,
                descriptionId = R.id.efficiencyCard2Description,
                iconId = R.id.efficiencyIcon2,
                titleRes = R.string.wave_height_title,
                descriptionRes = R.string.wave_height_description,
                iconRes = R.drawable.ic_wave_height
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard3,
                titleId = R.id.efficiencyCard3Title,
                descriptionId = R.id.efficiencyCard3Description,
                iconId = R.id.efficiencyIcon3,
                titleRes = R.string.wave_placement_title,
                descriptionRes = R.string.wave_placement_description,
                iconRes = R.drawable.ic_wave_placement
            ),
            EfficiencyCardData(
                cardId = R.id.efficiencyCard4,
                titleId = R.id.efficiencyCard4Title,
                descriptionId = R.id.efficiencyCard4Description,
                iconId = R.id.efficiencyIcon4,
                titleRes = R.string.wave_maintenance_title,
                descriptionRes = R.string.wave_maintenance_description,
                iconRes = R.drawable.ic_wave_maintenance
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