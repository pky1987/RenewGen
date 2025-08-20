package com.example.re

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlin.math.abs

class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private var currentIndex = 0
    private val handler = Handler()
    private lateinit var textView: TextView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private val texts = listOf(
        "Renewable Energy",
        "Welcome to Home Page",
        "Best Solar Energy App",
        "Pollution Free Energy Source",
        "Future Energy Source"
    )
    private val products = listOf(
        Energy("Solar Energy", "₹20000", R.drawable.solarenergy),
        Energy("Wind Energy", "₹15000", R.drawable.windenergy),
        Energy("Hydropower Energy", "₹12000", R.drawable.hydropower),
        Energy("Geothermal Energy", "₹18000", R.drawable.geothermal),
        Energy("Biomass Energy", "₹22000", R.drawable.biomass),
        Energy("Wave Energy", "₹25000", R.drawable.waveenergy)
    )

    companion object {
        const val CHANNEL_ID = "my_channel_id"
        const val NOTIFICATION_ID = 1
        const val PERMISSION_CODE = 1001
        val MESSAGES = listOf(
            "Harness the sun's energy to light up your world sustainably.",
            "Catch the breeze, generate power, and reduce emissions.",
            "Flowing water can fuel our future without fossil fuels.",
            "Tap into Earth's heat for a cleaner, greener energy source.",
            "Every watt saved is a step toward a sustainable planet.",
            "Reduce your carbon footprint with renewable choices today.",
            "Recycle today for a greener tomorrow.",
            "Live sustainably; the Earth will thank you.",
            "Drive green with electric vehicles and clean energy.",
            "Join the renewable revolution for a brighter future.",
            "Wind turbines turning today for a cleaner tomorrow.",
            "Solar panels: your roof can power your life.",
            "Conserve energy, preserve nature, sustain life.",
            "Innovate green, live clean, and keep the Earth serene.",
            "Support policies that prioritize renewable resources.",
            "Renewables today ensure a livable world tomorrow.",
            "Grow green with sustainable farming practices.",
            "Harness ocean power to energize our planet responsibly.",
            "Turn waste into energy with bio-renewable solutions.",
            "Build green; design with renewables in mind.",
            "Act now for a climate-safe renewable energy future.",
            "Plant trees, promote biodiversity, capture carbon.",
            "Educate to empower a renewable-powered society.",
            "Invest in renewables; dividends are a healthier planet.",
            "Break free from fossil fuels; embrace renewables.",
            "Unite globally for renewable energy solutions.",
            "Walk, bike, or electric—travel sustainably.",
            "Choose wisely; every purchase impacts the planet.",
            "Advance technology for affordable renewable energy.",
            "Together, we can power the world with renewables."
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupUIComponents(view)
        setupNotifications()
        setupNews(view)
        return view
    }

    private fun setupUIComponents(view: View) {
        val actionMenu: ImageView = view.findViewById(R.id.action_menu)
        actionMenu.setOnClickListener { toggleDrawer() }
        drawerLayout = view.findViewById(R.id.drawer_layout)
        navView = view.findViewById(R.id.navigation_view)
        textView = view.findViewById(R.id.txt)
        startChangingText()

        val movingText: TextView = view.findViewById(R.id.movingText)
        animateMovingText(movingText)
        setupRecyclerViews(view)
        setupNavigationComponents(view)
    }

    private fun setupRecyclerViews(view: View) {
        val horizontalRecyclerView1 = view.findViewById<RecyclerView>(R.id.recycler_view_horizontal_1)
        horizontalRecyclerView1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView1.adapter = Horizontal1Adapter(products)
        LinearSnapHelper().attachToRecyclerView(horizontalRecyclerView1)
        setupRecyclerViewIndicators(horizontalRecyclerView1, view)
    }

    private fun setupNews(view: View) {
        fun setupChip(chipId: Int, url: String) {
            view.findViewById<View>(chipId)?.setOnClickListener {
                try {
                    val webUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        "https://$url"
                    } else {
                        url
                    }
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)))
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error opening link", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setupChip(R.id.solarLearnMoreChip, "https://www.mercomindia.com/")
        setupChip(R.id.windLearnMoreChip, "https://renewablesnow.com/")
        setupChip(R.id.hydroLearnMoreChip, "https://www.investindia.gov.in/sector/renewable-energy")
        setupChip(R.id.geothermalLearnMoreChip, "https://gggi.org/renewable-energy-is-now-a-commercially-attractive-investment-opportunity/")
    }


    private fun setupNavigationComponents(view: View) {
        navView.setNavigationItemSelectedListener(this)
        view.findViewById<ImageView>(R.id.action_bar_icon).setOnClickListener {
            startActivity(Intent(requireContext(), NotificationMessagesActivity::class.java).apply {
                putStringArrayListExtra("messages", ArrayList(MESSAGES))
            })
        }
    }

    private fun setupNotifications() {
        val alarmManager = requireContext().getSystemService(AlarmManager::class.java)
        for (i in MESSAGES.indices) {
            val intent = Intent(requireContext(), NotificationReceiver::class.java).apply {
                putExtra("message", MESSAGES[i])
                putStringArrayListExtra("messages", ArrayList(MESSAGES))
            }
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                i,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val triggerTime = System.currentTimeMillis() + (i * 600000)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
        }
    }

    private fun animateMovingText(movingText: TextView) {
        movingText.post {
            val screenWidth = resources.displayMetrics.widthPixels.toFloat()
            val textWidth = movingText.width.toFloat()
            movingText.translationX = screenWidth
            ObjectAnimator.ofFloat(movingText, "translationX", screenWidth, -textWidth).apply {
                duration = 6000
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
            }.start()
        }
    }

    private fun setupRecyclerViewIndicators(recyclerView: RecyclerView, view: View) {
        val pointerContainer = view.findViewById<LinearLayout>(R.id.pointer_container)
        pointerContainer.removeAllViews()
        products.indices.forEach { _ ->
            pointerContainer.addView(View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(20.dpToPx(), 4.dpToPx()).apply {
                    setMargins(4.dpToPx(), 0, 4.dpToPx(), 0)
                }
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorGray))
            })
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                updateIndicatorPosition(recyclerView, pointerContainer)
            }
        })
    }

    private fun updateIndicatorPosition(recyclerView: RecyclerView, container: LinearLayout) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val centerX = recyclerView.width / 2
        var minDistance = Int.MAX_VALUE
        var centerPosition = 0
        for (i in 0 until layoutManager.childCount) {
            val child = layoutManager.getChildAt(i) ?: continue
            val childCenter = (child.left + child.right) / 2
            val distance = abs(childCenter - centerX)
            if (distance < minDistance) {
                minDistance = distance
                centerPosition = layoutManager.getPosition(child)
            }
        }
        for (i in 0 until container.childCount) {
            container.getChildAt(i).setBackgroundColor(
                if (i == centerPosition) ContextCompat.getColor(requireContext(), R.color.colorAccent)
                else ContextCompat.getColor(requireContext(), R.color.colorGray)
            )
        }
    }

    private fun startChangingText() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                textView.text = texts[currentIndex % texts.size]
                currentIndex++
                handler.postDelayed(this, 2000)
            }
        }, 2000)
    }

    private fun toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START)
        else drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home -> drawerLayout.closeDrawer(GravityCompat.START)
            R.id.profile -> startActivity(Intent(requireContext(), user_profile_show::class.java))
            R.id.payment -> startActivity(Intent(requireContext(), PaymentActivity::class.java))
            R.id.about_us -> startActivity(Intent(requireContext(), AboutUsActivity::class.java))
            R.id.ai -> startActivity(Intent(requireContext(), gen_ai::class.java))
            R.id.th -> startActivity(Intent(requireContext(), FeedbackActivity::class.java))
            R.id.setting -> startActivity(Intent(requireContext(), SettingsActivity::class.java))
            R.id.logout -> startActivity(Intent(requireContext(), LogoutActivity::class.java))
        }
        return true
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
