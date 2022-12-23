package com.monitoring.cellshark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.monitoring.cellshark.data.ButtonNames
import com.monitoring.cellshark.data.SERVICE_RUNNING
import com.monitoring.cellshark.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var isFABOpen = false
    lateinit var homeFAB: FloatingActionButton
    private lateinit var portFAB: FloatingActionButton
    private lateinit var serviceFAB: FloatingActionButton
    private val interpolator = OvershootInterpolator()
    private lateinit var serviceTV: TextView
    private lateinit var portCheckerTV: TextView
    private lateinit var homeTV: TextView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val cellSharkService = Intent(this, MetricsService::class.java)

        homeFAB = findViewById(R.id.home_fab)
        portFAB = findViewById(R.id.port_fab)
        serviceFAB = findViewById(R.id.service_fab)
        serviceTV = findViewById(R.id.service_tv)
        portCheckerTV = findViewById(R.id.port_checker_tv)
        homeTV = findViewById(R.id.menu_expand_tv)

        initFABMenu()
        SmartFileWriter().writeDirectories()

        homeFAB.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else closeFABMenu()
        }

        serviceFAB.setOnClickListener {

            //If the activity is no on the foreground, bring up the activity/layout, otherwise skip
            fragmentTransitionManager(navController, ButtonNames.METRICS)
            portCheckerTV.text = "Enter Port Checker"

            if (navController.currentDestination?.label == "MetricsFragment") {
                serviceTV.text = "Exit"

            } else {
                serviceTV.text = "View Device Metrics"
            }

//            if (!SERVICE_RUNNING) {
////                SERVICE_RUNNING = true
////                Snackbar.make(it, "Augmedix Service Started", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
////                startForegroundService(cellSharkService)
//                serviceFAB.animate().setInterpolator(interpolator).rotation(180f).setDuration(300).start()
//                serviceTV.text = "Enter Metrics Page"
//                serviceFAB.setImageDrawable(AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_pause_24))
//            }
//            else {
////                SERVICE_RUNNING = false
////                stopService(cellSharkService)
//                serviceFAB.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start()
//                serviceTV.text = "Exit Metrics page"
//                serviceFAB.setImageDrawable(AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_play_arrow_24))
//            }

        }

        portFAB.setOnClickListener {

            fragmentTransitionManager(navController, ButtonNames.ENDPOINTS)
            serviceTV.text = "View Device Metrics"

            if (navController.currentDestination?.label == "PortCheckerFragment") {
                portCheckerTV.text = "Exit Port Checker"
            } else portCheckerTV.text = "Enter Port Checker"

        }

    }

    private fun fragmentTransitionManager(navController: NavController, buttonPressed: ButtonNames){

        Log.d("FragmentTransitionManager", "navController Origin: ${navController.currentDestination?.label}")

        val origin = navController.currentDestination?.label


        //Exit navigation
        if (origin == buttonPressed.name) {
            when(buttonPressed) {
                ButtonNames.METRICS -> { navController.navigate(R.id.action_metricsFragment_to_homeFragment) }
                ButtonNames.ENDPOINTS -> { navController.navigate(R.id.action_portCheckerFragment_to_homeFragment) }
            }
        }
        //Enter Animation
        else {
            if (origin == "HomeFragment") {
                when (buttonPressed) {
                    ButtonNames.METRICS -> navController.navigate(R.id.action_homeFragment_to_metricsFragment)
                    ButtonNames.ENDPOINTS -> navController.navigate(R.id.action_homeFragment_to_portCheckerFragment)
                }
            }
            else if (origin == "MetricsFragment") {
                when (buttonPressed) {
                    ButtonNames.METRICS -> navController.navigate(R.id.action_metricsFragment_to_homeFragment)
                    ButtonNames.ENDPOINTS -> navController.navigate(R.id.action_metricsFragment_to_portCheckerFragment)
                }
            }
            else { //ENDPOINT FRAGMENT
                when (buttonPressed) {
                    ButtonNames.METRICS -> navController.navigate(R.id.action_portCheckerFragment_to_metricsFragment)
                    ButtonNames.ENDPOINTS -> navController.navigate(R.id.action_portCheckerFragment_to_homeFragment)
                }
            }
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }

    private fun initFABMenu() {

        portFAB.alpha = 0F
        portFAB.translationY = 100F
        serviceFAB.alpha = 0F
        serviceFAB.translationY = 100F

        serviceTV.visibility = View.VISIBLE
        portCheckerTV.visibility = View.VISIBLE
        serviceTV.alpha = 0F
        serviceTV.translationY = 100F
        portCheckerTV.alpha = 0F
        portCheckerTV.translationY = 100F

    }

    private fun showFABMenu() {
        isFABOpen = true

        homeFAB.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start()
        homeTV.text = "Close Menu"
        portFAB.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        portCheckerTV.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        serviceFAB.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        serviceTV.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
    }

    private fun closeFABMenu() {
        isFABOpen = false
        homeTV.text = "Open Menu"
        homeFAB.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start()
        serviceTV.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        portCheckerTV.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        serviceFAB.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        portFAB.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()

    }
}