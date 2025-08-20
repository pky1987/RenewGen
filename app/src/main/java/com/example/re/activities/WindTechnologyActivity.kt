package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class WindTechnologyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wind_technology)

        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.wind_technology_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.wind_technology_description)

        setupTechnologyCards()
    }

    private fun setupTechnologyCards() {
        val technologyData = listOf(
            TechnologyCardData(
                cardId = R.id.technologyCard1,
                titleId = R.id.technologyCard1Title,
                descriptionId = R.id.technologyCard1Description,
                iconId = R.id.technologyIcon1,
                titleRes = R.string.horizontal_axis_title,
                descriptionRes = R.string.horizontal_axis_description,
                iconRes = R.drawable.ic_horizontal_axis
            ),
            TechnologyCardData(
                cardId = R.id.technologyCard2,
                titleId = R.id.technologyCard2Title,
                descriptionId = R.id.technologyCard2Description,
                iconId = R.id.technologyIcon2,
                titleRes = R.string.vertical_axis_title,
                descriptionRes = R.string.vertical_axis_description,
                iconRes = R.drawable.ic_vertical_axis
            ),
            TechnologyCardData(
                cardId = R.id.technologyCard3,
                titleId = R.id.technologyCard3Title,
                descriptionId = R.id.technologyCard3Description,
                iconId = R.id.technologyIcon3,
                titleRes = R.string.offshore_wind_title,
                descriptionRes = R.string.offshore_wind_description,
                iconRes = R.drawable.ic_offshore_wind
            ),
            TechnologyCardData(
                cardId = R.id.technologyCard4,
                titleId = R.id.technologyCard4Title,
                descriptionId = R.id.technologyCard4Description,
                iconId = R.id.technologyIcon4,
                titleRes = R.string.wind_hybrid_title,
                descriptionRes = R.string.wind_hybrid_description,
                iconRes = R.drawable.ic_wind_hybrid
            )
        )

        technologyData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<ImageView>(data.iconId).setImageResource(data.iconRes)
                findViewById<TextView>(data.titleId).setText(data.titleRes)
                findViewById<TextView>(data.descriptionId).setText(data.descriptionRes)
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