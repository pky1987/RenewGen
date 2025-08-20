package com.example.re.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class GeothermalTechnologyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geothermal_technology)

        try {
            // Set main title and description
            findViewById<TextView>(R.id.technologyTitle)?.apply {
                text = getString(R.string.geothermal_technology_title)
            }
            findViewById<TextView>(R.id.technologyDescription)?.apply {
                text = getString(R.string.geothermal_technology_description)
            }

            setupTechnologyCards()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupTechnologyCards() {
        val technologyData = listOf(
            CardData(
                R.string.geothermal_egs_title,
                R.string.geothermal_egs_description,
                R.drawable.ic_geothermal_egs,
                R.id.technologyCard1,
                R.id.technologyIcon1,
                R.id.technologyTitle1,
                R.id.technologyDescription1
            ),
            CardData(
                R.string.geothermal_heat_pump_title,
                R.string.geothermal_heat_pump_description,
                R.drawable.ic_geothermal_heat_pump,
                R.id.technologyCard2,
                R.id.technologyIcon2,
                R.id.technologyTitle2,
                R.id.technologyDescription2
            ),
            CardData(
                R.string.geothermal_hydrothermal_title,
                R.string.geothermal_hydrothermal_description,
                R.drawable.ic_geothermal_hydrothermal,
                R.id.technologyCard3,
                R.id.technologyIcon3,
                R.id.technologyTitle3,
                R.id.technologyDescription3
            ),
            CardData(
                R.string.geothermal_hybrid_title,
                R.string.geothermal_hybrid_description,
                R.drawable.ic_geothermal_hybrid,
                R.id.technologyCard4,
                R.id.technologyIcon4,
                R.id.technologyTitle4,
                R.id.technologyDescription4
            )
        )

        technologyData.forEach { data ->
            try {
                findViewById<MaterialCardView>(data.cardId)?.apply {
                    findViewById<ImageView>(data.iconId)?.setImageResource(data.iconRes)
                    findViewById<TextView>(data.titleId)?.setText(data.titleRes)
                    findViewById<TextView>(data.descriptionId)?.setText(data.descriptionRes)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private data class CardData(
        val titleRes: Int,
        val descriptionRes: Int,
        val iconRes: Int,
        val cardId: Int,
        val iconId: Int,
        val titleId: Int,
        val descriptionId: Int
    )
}
