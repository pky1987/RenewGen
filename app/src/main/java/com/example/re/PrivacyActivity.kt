package com.example.re

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        setupExpandableSection(R.id.dataCollectionCard, R.id.dataCollectionContent, R.id.dataCollectionArrow)
        setupExpandableSection(R.id.dataUsageCard, R.id.dataUsageContent, R.id.dataUsageArrow)
        setupExpandableSection(R.id.userRightsCard, R.id.userRightsContent, R.id.userRightsArrow)

        findViewById<CheckBox>(R.id.consentCheckbox).setOnCheckedChangeListener { _, isChecked ->
            findViewById<View>(R.id.acceptButton).isEnabled = isChecked
        }

        findViewById<View>(R.id.acceptButton).setOnClickListener {
            // Handle policy acceptance
            finish()
        }

        findViewById<View>(R.id.fullPolicyButton).setOnClickListener {
            startActivity(Intent(this, FullPolicyActivity::class.java))
        }
    }

    private fun setupExpandableSection(cardViewId: Int, contentId: Int, arrowId: Int) {
        val cardView = findViewById<MaterialCardView>(cardViewId)
        val content = findViewById<View>(contentId)
        val arrow = findViewById<View>(arrowId)

        cardView.setOnClickListener {
            if (content.visibility == View.VISIBLE) {
                content.visibility = View.GONE
                arrow.animate().rotation(0f).start()
            } else {
                content.visibility = View.VISIBLE
                arrow.animate().rotation(180f).start()
            }
        }
    }
}