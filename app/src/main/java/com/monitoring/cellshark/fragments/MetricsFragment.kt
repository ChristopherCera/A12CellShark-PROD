package com.monitoring.cellshark.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.monitoring.cellshark.MetricsService
import com.monitoring.cellshark.R
import com.monitoring.cellshark.Utility
import com.monitoring.cellshark.data.MetricsEvent
import com.monitoring.cellshark.data.SERVICE_RUNNING
import com.monitoring.cellshark.databinding.MetricsFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MetricsFragment: Fragment(R.layout.metrics_fragment) {


    private var _binding: MetricsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = MetricsFragmentBinding.inflate(inflater, container, false)
        if (SERVICE_RUNNING) { binding.metricsServiceButton.text = "Stop Service" }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val cellSharkService = Intent(context, MetricsService::class.java)

        binding.metricsServiceButton.setOnClickListener {
            if(!SERVICE_RUNNING) {
                SERVICE_RUNNING = true
                Snackbar.make(it, "Augmedix Service Started", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
                activity?.startForegroundService(cellSharkService)
                binding.metricsServiceButton.text = "Stop Service"
            }
            else {
                SERVICE_RUNNING = false
                activity?.stopService(cellSharkService)
                binding.metricsServiceButton.text = "Start Service"
            }
        }

        val data = EventBus.getDefault().getStickyEvent(MetricsEvent::class.java)
        if (data == null) {
            Log.d("Event_State", "Event is Null")
        } else onMetricsUpdate(data)

        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    fun onMetricsUpdate(event: MetricsEvent) {

        //WIFI Update
        binding.connectedSSIDLabel.text = event.wm.ssid
        binding.connectedBSSIDLabel.text = event.wm.bssid
        binding.rssiLabel.text = event.wm.rssi.toString()
        binding.linkRateLabel.text = "${event.wm.linkSpeed} Mbps"
        binding.deviceIPLabel.text = Formatter.formatIpAddress(event.wm.ipAddress)
        binding.wifiBandLabel.text = Utility.getBand(event.wm.frequency)
        binding.wifiChannelLabel.text = Utility.getChannel(event.wm.frequency).toString()
        binding.wifiTechLabel.text = event.wifiCapability
        binding.simStateLabel.text = event.lteSimState

        //LTE UPDATE
        binding.cellRsrpLabel.text = event.lteRSRP
        binding.cellRsrqLabel.text = event.lteRSRQ
        binding.cellBandLabel.text = event.lteBand
        binding.lteStateLabel.text = event.dataState

    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

}