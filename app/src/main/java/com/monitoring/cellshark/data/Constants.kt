package com.monitoring.cellshark.data

enum class EndpointType {
    PING, GET
}

object Constants {
    const val PARENT = 0
    const val CHILD = 1
}

var SERVICE_RUNNING = false
const val PREFS_SERIAL              = "PreferenceSerial"
const val PREFS_SERIAL_KEY          = "PreferenceSerialKey"

const val REQUEST_CODE              = 101
const val CHANNEL_ID                = "com.monitoring.cellshark"
const val APP_NAME                  = "CellShark"

val endpoints = arrayOf(
    Endpoint(arrayOf("https://cellshark.augmedix.com:1200"), "S.M.A.R.T.", EndpointType.GET, "Connection Health Metrics"),
    Endpoint(arrayOf("https://cloudservice.augmedix.com"), "Cloud Service Wrapper", EndpointType.GET, "Wrap Cloud URL which are accessible from SCP or DocApp with this URL"),
    Endpoint(arrayOf("https://d1gi4osejmfp4a.cloudfront.net", "https://dcz6tw7elxkkx.cloudfront.net"),"Content Delivery Networks", EndpointType.GET, "Static front end assets are requested and received"),
    Endpoint(arrayOf("https://ee-portal.augmedix.com"), "Augmedix Customer Portal", EndpointType.GET, "Customer Login Portal"),
//    "https://datachannel.augmedix.com",
//    "https://datachannel-fallback.augmedix.com",
    Endpoint(arrayOf("https://uploader.augmedix.com"),"File Server", EndpointType.GET, "Handles file upload process for both DocApp and SCP"),
    Endpoint(arrayOf("https://messaging.augmedix.com"), "Augmedix Messaging Platform", EndpointType.GET, "App messaging service"),
//    "https://cd-mcu-rec.augmedix.com:9090/v1/ping",
//    "https://cd-mcu-rec1.augmedix.com:9090/v1/ping",
//    "https://cd-mcu-rec2.augmedix.com:9090/v1/ping",
    Endpoint(arrayOf("https://api.augmedix.com"), "Logentries", EndpointType.GET, "Application and device logs"),
    Endpoint(arrayOf("https://mcu3.augmedix.com:9090/v1/ping/", "https://mcu4.augmedix.com:9090/v1/ping", "https://mcu6.augmedix.com:9090/v1/ping"),
        "MCU", EndpointType.GET, "Streaming endpoints"),
    Endpoint(arrayOf("http://api.augmedix.com:50001"), "Augmedix API", EndpointType.GET, "Microservice API"),
    Endpoint(arrayOf("35.71.161.128", "52.223.21.77"), "Augmedix APIv2", EndpointType.GET, "Microservice in global Accelerator"),
    Endpoint( arrayOf("https://cn705.awmdm.com", "https://ds705.awmdm.com","https://awcm705.awmdm.com", "na1.data.vmwservices.com"),
                    "Airwatch Mobile Device Management Suite", EndpointType.GET, "Android Mobile Device Management Suite")
//    Endpoint(arrayOf("play.google.com", "https://www.android.com",
//        "android.clients.google.com", "https://www.google-analytics.com", "https://www.googleusercontent.com",
//        "https://www.gstatic.com", "https://dl.google.com", "https://www.googleapis.com"), "Google Endpoints", EndpointType.GET, "Required for full MDM functionality"),
//    "https://www.ntppool.org/en/",
//    "https://ota.augmedix.com",
//    "https://augmedix-prod-ota.s3-us-west-2.amazonaws.com",
//    "https://cdnus04.awmdm.com",
//    "https://cn705.awmdm.com",
//    "https://ds705.awmdm.com",
//    "https://awcm705.awmdm.com",
//    "https://na1.data.vmwservices.com",
//    "https://play.google.com",
//    "https://www.android.com",
//    "https://android.clients.google.com/",
//    "https://dl.google.com",
//    "https://yt3.ggpht.com/",
//    "https://accounts.google.com",
//    "https://fcm.googleapis.com",
//    "https://pki.google.com",
//    "https://clients1.google.com",
//    "https://clients2.google.com",
//    "https://clients3.google.com",
//    "https://clients4.google.com",
//    "https://clients5.google.com",
//    "https://clients6.google.com",
//    "https://kinesis.us-east-1.amazonaws.com",
//    "https://kinesis.us-west-1.amazonaws.com",
//    "https://kinesis.us-west-2.amazonaws.com",
//    "https://kinesis.us-east-2.amazonaws.com",
//    "https://cognito-identity.us-east-1.amazonaws.com",
//    "https://cognito-identity.us-east-2.amazonaws.com",
//    "https://cognito-identity.us-west-2.amazonaws.com",
//    "https://www.google.com/generate_204",
//    "https://clients3.google.com/generate_204",
//    "https://android.googleapis.com",
//    "https://gcm-http.googleapis.com",
//    "https://eu.ops.insight.rapid7.com/",
//    "https://insight.rapid7.com/login"
)

val endpointPING = arrayOf(
    Pair("52.223.52.124", "Backup API GA"),
    Pair("35.71.178.142", "Backup API GA"),
    Pair("52.223.48.71", "Backup Websocket GA"),
    Pair("35.71.150.142", "Backup Websocket GA"),
    Pair("35.71.150.142", "Backup Websocket GA"),




)