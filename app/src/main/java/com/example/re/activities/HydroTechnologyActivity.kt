package com.example.re.activities
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView
class HydroTechnologyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hydro_technology)
        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.hydro_technology_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.hydro_technology_description)
        setupTechnologyCards()
    }
    private fun setupTechnologyCards() {
        val technologyData = listOf(
            TechnologyCardData(
                R.id.technologyCard1,
                R.id.technologyCard1Title,
                R.id.technologyCard1Description,
                R.id.technologyIcon1,
                R.string.hydro_run_of_river_title,
                R.string.hydro_run_of_river_description,
                R.drawable.ic_hydro_run_of_river
            ),
            TechnologyCardData(
                R.id.technologyCard2,
                R.id.technologyCard2Title,
                R.id.technologyCard2Description,
                R.id.technologyIcon2,
                R.string.hydro_reservoir_title,
                R.string.hydro_reservoir_description,
                R.drawable.ic_hydro_reservoir
            ),
            TechnologyCardData(
                R.id.technologyCard3,
                R.id.technologyCard3Title,
                R.id.technologyCard3Description,
                R.id.technologyIcon3,
                R.string.hydro_pumped_storage_title,
                R.string.hydro_pumped_storage_description,
                R.drawable.ic_hydro_pumped_storage
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