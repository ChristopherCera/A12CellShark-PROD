package com.monitoring.cellshark.data

import android.annotation.SuppressLint
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.telephony.CellInfoLte
import android.telephony.TelephonyManager

class EventBusDeviceMetrics {

    var wm: WifiInfo
    lateinit var lteRSRP: String
    lateinit var lteRSRQ: String
    lateinit var lteBand: String
    var lteSimState: String
    var wifiCapability: String
    var dataState: String

    @SuppressLint("MissingPermission")
    constructor(tm: TelephonyManager, wm: WifiManager) {
        dataState = getDataState(tm.dataState)
        lteSimState = getSimState(tm.simState)
        wifiCapability = getWiFiStandard(wm)
        this.wm = wm.connectionInfo
        tm.allCellInfo.forEach { event ->
            when(event) {
                is CellInfoLte -> {
                    if (event.isRegistered) {
                        lteRSRP = event.cellSignalStrength.rsrp.toString()
                        lteRSRQ = event.cellSignalStrength.rsrq.toString()
                        lteBand = getCellBand(event.cellIdentity.earfcn)
                    }
                }
            }
        }
    }

    private fun getDataState(value: Int): String {
        return when(value) {
            TelephonyManager.DATA_DISCONNECTED -> return "Disconnected"
            TelephonyManager.DATA_SUSPENDED -> return "Suspended"
            TelephonyManager.DATA_UNKNOWN -> return "Unknown"
            TelephonyManager.DATA_CONNECTING -> return "Connecting"
            TelephonyManager.DATA_CONNECTED -> return "Connected"
            else -> "N/A"
        }
    }

    private fun getCellBand(value: Int): String {
        return when (value) {
            //in 0..599 -> cellBand = 1
            in 600..1199 -> "2"
            in 1950..2399 -> "4"
            in 2400..2649 -> "5"
            in 5180..5279 -> "13"
            in 66436..67335 -> "66"
            else -> "N/A"
        }
    }

    @SuppressLint("MissingPermission")
    private fun getWiFiStandard(wm: WifiManager): String {

        val connectedBSSID = wm.connectionInfo.bssid
        val list = wm.scanResults

        var value: Int? = null
        list.forEach {
            if (connectedBSSID == it.BSSID) value = it.wifiStandard
        }

        if (value == null) return "N/A"


        return when(value) {

            ScanResult.WIFI_STANDARD_11AC ->    { "802.11AC" }
            ScanResult.WIFI_STANDARD_11AX ->    { "802.11AX" }
            ScanResult.WIFI_STANDARD_11N ->     { "802.11N" }
            ScanResult.WIFI_STANDARD_LEGACY ->  { "LEGACY" }
            ScanResult.WIFI_STANDARD_UNKNOWN -> { "UNKNOWN" }
            else -> { "N/A" }

        }
    }

    private fun getSimState(value: Int): String {
        return when(value) {

            TelephonyManager.SIM_STATE_ABSENT ->            {
                "Absent"
            }
            TelephonyManager.SIM_STATE_CARD_IO_ERROR ->     {
                "IO Error"
            }
            TelephonyManager.SIM_STATE_CARD_RESTRICTED ->   {
                "Restricted"
            }
            TelephonyManager.SIM_STATE_NETWORK_LOCKED ->    {
                "Network Locked"
            }
            TelephonyManager.SIM_STATE_NOT_READY ->         {
                "Not Ready"
            }
            TelephonyManager.SIM_STATE_PERM_DISABLED ->     {
                "Permanently Disabled"
            }
            TelephonyManager.SIM_STATE_PIN_REQUIRED ->      {
                "PIN Required"
            }
            TelephonyManager.SIM_STATE_PUK_REQUIRED ->      {
                "PUK Required"
            }
            TelephonyManager.SIM_STATE_READY ->             {
                "Ready"
            }
            TelephonyManager.SIM_STATE_UNKNOWN ->           {
                "Unknown"
            }
            else -> {
                "N/A"
            }
        }
    }

}