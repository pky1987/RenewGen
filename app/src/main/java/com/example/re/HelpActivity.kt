package com.example.re
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.android.material.card.MaterialCardView

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        setupExpandableSection(R.id.accountSection, R.id.accountContent, R.id.accountArrow)
        setupExpandableSection(R.id.dataSection, R.id.dataContent, R.id.dataArrow)

        findViewById<View>(R.id.emailButton).setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:renewable_energyhelp1@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Renewable Energy App Support")
                startActivity(this)
            }
        }

        findViewById<View>(R.id.callButton).setOnClickListener {
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+91 9076263616")
                startActivity(this)
            }
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