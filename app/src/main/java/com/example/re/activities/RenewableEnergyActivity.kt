package com.example.re.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.re.BiomassEnergyActivity
import com.example.re.GeothermalEnergyActivity
import com.example.re.HydroEnergyActivity
import com.example.re.R
import com.example.re.SolarEnergyActivity
import com.example.re.WaveEnergyActivity
import com.example.re.WindEnergyActivity

class RenewableEnergyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renewable_energy)

        findViewById<View>(R.id.solarEnergyCard).setOnClickListener {
            startActivity(Intent(this, SolarEnergyActivity::class.java))
        }

        findViewById<View>(R.id.windEnergyCard).setOnClickListener {
            startActivity(Intent(this, WindEnergyActivity::class.java))
        }

        findViewById<View>(R.id.hydroEnergyCard).setOnClickListener {
            startActivity(Intent(this, HydroEnergyActivity::class.java))
        }

        findViewById<View>(R.id.geothermalEnergyCard).setOnClickListener {
            startActivity(Intent(this, GeothermalEnergyActivity::class.java))
        }

        findViewById<View>(R.id.biomassEnergyCard).setOnClickListener {
            startActivity(Intent(this, BiomassEnergyActivity::class.java))
        }

        findViewById<View>(R.id.waveEnergyCard).setOnClickListener {
            startActivity(Intent(this, WaveEnergyActivity::class.java))
        }

        findViewById<View>(R.id.solarLearnMoreChip).setOnClickListener {
            val url = "https://www.youtube.com/watch?v=Yxt72aDjFgY&pp=ygUUc29sYXIgZW5lcmd5IGVuZ2xpc2g%3D"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        findViewById<View>(R.id.windLearnMoreChip).setOnClickListener {
            val url = "https://www.youtube.com/watch?v=xy9nj94xvKA&pp=ygUTd2luZCBlbmVyZ3kgZW5nbGlzaA%3D%3D"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        findViewById<View>(R.id.geothermalLearnMoreChip).setOnClickListener {
            val url = "https://www.youtube.com/watch?v=9zcfd6IH_QQ&pp=ygUYZ2VvdGhlcm1hbGVuZXJneSBlbmdsaXNo"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        findViewById<View>(R.id.hydroLearnMoreChip).setOnClickListener {
            val url = "https://www.youtube.com/watch?v=q8HmRLCgDAI&pp=ygUUaHlkcm8gZW5lcmd5IGVuZ2xpc2g%3D"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        findViewById<View>(R.id.biomassLearnMoreChip).setOnClickListener {
            val url = "https://www.youtube.com/watch?v=Cux0Xwvy0cU&pp=ygUWYmlvbWFzcyBlbmVyZ3kgZW5nbGlzaA%3D%3D"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        findViewById<View>(R.id.waveLearnMoreChip).setOnClickListener {
            val url = "https://www.youtube.com/watch?v=go82UOFlKPE&pp=ygUTd2F2ZSBlbmVyZ3kgZW5nbGlzaA%3D%3D"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}