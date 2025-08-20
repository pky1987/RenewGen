package com.example.re.activities
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class HydroProductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hydro_production)
        findViewById<TextView>(R.id.titleTextView).text = getString(R.string.hydro_production_title)
        findViewById<TextView>(R.id.descriptionTextView).text = getString(R.string.hydro_production_description)
        setupProductionCards()
    }

    private fun setupProductionCards() {
        val productionData = listOf(
            ProductionCardData(
                R.id.productionCard1,
                R.id.productionCard1Title,
                R.id.productionCard1Description,
                R.string.hydro_annual_production_title,
                R.string.hydro_annual_production_description
            ),
            ProductionCardData(
                R.id.productionCard2,
                R.id.productionCard2Title,
                R.id.productionCard2Description,
                R.string.hydro_capacity_factor_title,
                R.string.hydro_capacity_factor_description
            ),
            ProductionCardData(
                R.id.productionCard3,
                R.id.productionCard3Title,
                R.id.productionCard3Description,
                R.string.hydro_power_density_title,
                R.string.hydro_power_density_description
            )
        )

        productionData.forEach { data ->
            findViewById<MaterialCardView>(data.cardId).apply {
                findViewById<TextView>(data.titleId).text = getString(data.titleRes)
                findViewById<TextView>(data.descriptionId).text = getString(data.descriptionRes)
            }
        }
    }

    private data class ProductionCardData(
        val cardId: Int,
        val titleId: Int,
        val descriptionId: Int,
        val titleRes: Int,
        val descriptionRes: Int
    )
}