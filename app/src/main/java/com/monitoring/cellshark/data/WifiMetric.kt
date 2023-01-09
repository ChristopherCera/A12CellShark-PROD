package com.monitoring.cellshark.data

import android.annotation.SuppressLint
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.text.format.Formatter
import com.monitoring.cellshark.Utility

@SuppressLint("MissingPermission")
class WifiMetric(wm: WifiManager) {

    var wifiMetricsArray : Array<String>? = null
    private val increaseFrequencyValue: Int = -71


    init {
        val connectionInfo = wm.connectionInfo
        if (connectionInfo.bssid != null) {

            //Grab metric values
            val connectedSSID = connectionInfo.ssid.replace("\"", "")
            val connectedBSSID = connectionInfo.bssid.replace("\"", "")
            val deviceIP = Formatter.formatIpAddress(connectionInfo.ipAddress)
            val channel = Utility.getChannel(connectionInfo.frequency).toString()
            val signalStrength = (if (connectionInfo.rssi > -95) connectionInfo.rssi else 0).toString()
            val linkSpeed = (if ( connectionInfo.linkSpeed >= 1 ) connectionInfo.linkSpeed else 0).toString()

            //Increase frequency?
            increaseLogFrequency = increaseFrequencyValue > signalStrength.toInt()

            val neighborBssidList = mutableListOf<ScanResult>()

            //Grab all neighboring bssids
            wm.scanResults.forEach {

                if ( it.SSID != connectedSSID )     return@forEach
                if ( it.BSSID == connectedBSSID )   return@forEach

                neighborBssidList.add(it)

            }

            val metricsArray = mutableListOf<String>()
            metricsArray.add(type_wifi)
            metricsArray.add(Utility.getTimeStamp(DateFormat.EpochTime))
            metricsArray.add(deviceIP)
            metricsArray.add(connectedSSID)
            metricsArray.add(connectedBSSID)
            metricsArray.add(signalStrength)
            metricsArray.add(channel)
            metricsArray.add(linkSpeed)

            if (neighborBssidList.getOrNull(0) != null ) {
                metricsArray.add(neighborBssidList[0].BSSID)
                metricsArray.add(neighborBssidList[0].level.toString())
                metricsArray.add(Utility.getChannel(neighborBssidList[0].frequency).toString())
            } else for (x in 0..2) metricsArray.add("0")

            for (index in 1..4) {

                val neighbor = neighborBssidList.getOrNull(index)
                if (neighbor == null) for (x in 0..1) metricsArray.add("0")

                else {
                    metricsArray.add(neighbor.BSSID)
                    metricsArray.add(neighbor.level.toString())
                }

            }

            wifiMetricsArray = metricsArray.toTypedArray()

        }
    }

}