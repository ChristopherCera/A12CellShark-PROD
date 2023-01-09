package com.monitoring.cellshark

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import com.monitoring.cellshark.data.ConnectionInterface
import com.monitoring.cellshark.data.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    fun isWifiOffline(wm: WifiManager): Boolean {
        return run {
            val netInfo = wm.connectionInfo
            (wm.wifiState == WifiManager.WIFI_STATE_ENABLED
                    && (netInfo.bssid != null && netInfo.bssid != "02:00:00:00:00"))
        }
    }

    fun getTimeStamp(dateType: DateFormat): String {
        if (dateType == DateFormat.EpochTime ) return System.currentTimeMillis().toString()
        val time = SimpleDateFormat(dateType.format, Locale.getDefault())
        time.timeZone = TimeZone.getTimeZone("UTC")
        return time.format(Date())
    }
    @SuppressLint("MissingPermission")
    fun getActiveConnectionInterface(context: Context?): ConnectionInterface {

        if (context == null) return ConnectionInterface.OFFLINE

        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return ConnectionInterface.OFFLINE
        val activeNetwork = cm.getNetworkCapabilities(network) ?: return ConnectionInterface.OFFLINE
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionInterface.WIFI
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionInterface.CELLULAR
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionInterface.ETHERNET
            else -> ConnectionInterface.OFFLINE
        }
    }
    fun getBand(x: Int): String {
        return when {
            x == 0 -> "N/A"
            getChannel(x) > 12 -> "5"
            else -> "2.4"
        }
    }
    fun getChannel(channel: Int?): Int {

        when (channel) {

            2412 -> return 1
            2417 -> return 2
            2422 -> return 3
            2427 -> return 4
            2432 -> return 5
            2437 -> return 6
            2442 -> return 7
            2447 -> return 8
            2452 -> return 9
            2457 -> return 10
            2462 -> return 11
            5180 -> return 36
            5190 -> return 38
            5200 -> return 40
            5210 -> return 42
            5220 -> return 44
            5230 -> return 46
            5240 -> return 48
            5250 -> return 50
            5260 -> return 52
            5270 -> return 54
            5280 -> return 56
            5290 -> return 58
            5300 -> return 60
            5310 -> return 62
            5320 -> return 64
            5500 -> return 100
            5510 -> return 102
            5520 -> return 104
            5530 -> return 106
            5540 -> return 108
            5550 -> return 110
            5560 -> return 112
            5570 -> return 114
            5580 -> return 116
            5590 -> return 118
            5600 -> return 120
            5610 -> return 122
            5620 -> return 124
            5630 -> return 126
            5640 -> return 128
            5650 -> return 130
            5660 -> return 132
            5670 -> return 134
            5680 -> return 136
            5690 -> return 138
            5700 -> return 140
            5710 -> return 142
            5720 -> return 144
            5745 -> return 149
            5755 -> return 151
            5765 -> return 153
            5775 -> return 155
            5785 -> return 157
            5795 -> return 159
            5805 -> return 161
            5825 -> return 165
            else -> return 0

        }

    }

}