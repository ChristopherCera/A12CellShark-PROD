package com.monitoring.cellshark

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.IBinder
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.widget.Toast

class MetricsService: Service() {

    private lateinit var pm: PowerManager
    private lateinit var wm: WifiManager
    private lateinit var tm: TelephonyManager

    private lateinit var w1: PowerManager.WakeLock
    private lateinit var wifiLock: WifiManager.WifiLock

    private val metricsHandler = Handler()
    private lateinit var monitoringRunnable: Runnable

    override fun onCreate() {

        startNotification()
        pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        wm = getSystemService(Context.WIFI_SERVICE) as WifiManager

        w1 = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SMART: Wakelock")
        w1.acquire()
        wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "SMART: WiFiLock")
        wifiLock.acquire()

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        SERVICE_RUNNING = true
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun startNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_MIN
        val notificationChannel = NotificationChannel(CHANNEL_ID, APP_NAME, importance)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(notificationChannel)
        val foregroundIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, foregroundIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Smart")
            .setContentIntent(pendingIntent)
            .setContentText("Augmedix Service Running").build()
        startForeground(REQUEST_CODE, notification)
        Toast.makeText(this, "Augmedix Service Running", Toast.LENGTH_SHORT).show()
    }
}