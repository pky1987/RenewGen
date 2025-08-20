package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class SolarTechnologyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_technology)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.solar_technology_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.solar_technology_description)
        setupTechnologyCards()
    }

    private fun setupTechnologyCards() {
        val technologyData = listOf(
            TechnologyCardData(
                R.id.technologyCard1,
                R.id.technologyCard1Title,
                R.id.technologyCard1Description,
                R.id.technologyIcon1,
                R.string.photovoltaic_title,
                R.string.photovoltaic_description,
                R.drawable.ic_photovoltaic
            ),
            TechnologyCardData(
                R.id.technologyCard2,
                R.id.technologyCard2Title,
                R.id.technologyCard2Description,
                R.id.technologyIcon2,
                R.string.concentrated_solar_title,
                R.string.concentrated_solar_description,
                R.drawable.ic_concentrated_solar
            ),
            TechnologyCardData(
                R.id.technologyCard3,
                R.id.technologyCard3Title,
                R.id.technologyCard3Description,
                R.id.technologyIcon3,
                R.string.solar_thermal_title,
                R.string.solar_thermal_description,
                R.drawable.ic_solar_thermal
            ),
            TechnologyCardData(
                R.id.technologyCard4,
                R.id.technologyCard4Title,
                R.id.technologyCard4Description,
                R.id.technologyIcon4,
                R.string.solar_hybrid_title,
                R.string.solar_hybrid_description,
                R.drawable.ic_solar_hybrid
            )
        )

        technologyData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }

    private data class TechnologyCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val iconId: Int,
        val titleRes: Int,
        val descriptionRes: Int,
        val iconRes: Int
    )
}