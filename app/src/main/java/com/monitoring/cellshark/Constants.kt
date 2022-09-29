package com.monitoring.cellshark

var SERVICE_RUNNING = false
const val PREFS_SERIAL              = "PreferenceSerial"
const val PREFS_SERIAL_KEY          = "PreferenceSerialKey"

const val REQUEST_CODE              = 101
const val CHANNEL_ID                = "com.monitoring.cellshark"
const val APP_NAME                  = "CellShark"

val serviceEndpointsGET = arrayOf(
    //NEW LIST STARTS HEREs
    "https://cloudservice.augmedix.com",
    "https://d1gi4osejmfp4a.cloudfront.net",
    "https://dcz6tw7elxkkx.cloudfront.net",
    "https://datachannel.augmedix.com",
    "https://datachannel-fallback.augmedix.com",
    "https://uploader.augmedix.com",
    "https://messaging.augmedix.com",
    "https://mcu3.augmedix.com:9090/v1/ping",
    "https://mcu4.augmedix.com:9090/v1/ping",
    "https://mcu6.augmedix.com:9090/v1/ping",
    "https://cd-mcu-rec.augmedix.com:9090/v1/ping",
    "https://cd-mcu-rec1.augmedix.com:9090/v1/ping",
    "https://cd-mcu-rec2.augmedix.com:9090/v1/ping",
    "https://api.augmedix.com",
    "https://www.ntppool.org/en/",
    "https://ota.augmedix.com",
    "https://augmedix-prod-ota.s3-us-west-2.amazonaws.com",
    "https://cdnus04.awmdm.com",
    "https://cn705.awmdm.com",
    "https://ds705.awmdm.com",
    "https://awcm705.awmdm.com",
    "https://na1.data.vmwservices.com",
    "https://play.google.com",
    "https://www.android.com",
    "https://android.clients.google.com/",
    "https://dl.google.com",
    "https://yt3.ggpht.com/",
    "https://accounts.google.com",
    "https://fcm.googleapis.com",
    "https://pki.google.com",
    "https://clients1.google.com",
    "https://clients2.google.com",
    "https://clients3.google.com",
    "https://clients4.google.com",
    "https://clients5.google.com",
    "https://clients6.google.com",
    "https://kinesis.us-east-1.amazonaws.com",
    "https://kinesis.us-west-1.amazonaws.com",
    "https://kinesis.us-west-2.amazonaws.com",
    "https://kinesis.us-east-2.amazonaws.com",
    "https://cognito-identity.us-east-1.amazonaws.com",
    "https://cognito-identity.us-east-2.amazonaws.com",
    "https://cognito-identity.us-west-2.amazonaws.com",
    "https://www.google.com/generate_204",
    "https://clients3.google.com/generate_204",
    "https://android.googleapis.com",
    "https://gcm-http.googleapis.com",
    "https://eu.ops.insight.rapid7.com/",
    "https://insight.rapid7.com/login"
)