package com.monitoring.cellshark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var isFABOpen = false
    lateinit var homeFAB: FloatingActionButton
    private lateinit var portFAB: FloatingActionButton
    lateinit var serviceFAB: FloatingActionButton
    private val interpolator = OvershootInterpolator()
    private lateinit var serviceTV: TextView
    private lateinit var portCheckerTV: TextView
    private lateinit var homeTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        homeFAB = findViewById(R.id.home_fab)
        portFAB = findViewById(R.id.port_fab)
        serviceFAB = findViewById(R.id.service_fab)
        serviceTV = findViewById(R.id.service_tv)
        portCheckerTV = findViewById(R.id.port_checker_tv)
        homeTV = findViewById(R.id.menu_expand_tv)

        initFABMenu()

        homeFAB.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else closeFABMenu()
        }


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
        portFAB.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        portCheckerTV.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        serviceFAB.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        serviceTV.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
    }

    private fun closeFABMenu() {
        isFABOpen = false
        homeFAB.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start()
        serviceTV.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        portCheckerTV.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        serviceFAB.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        portFAB.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()

    }
}