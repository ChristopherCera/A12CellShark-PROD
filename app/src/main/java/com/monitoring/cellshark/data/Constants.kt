package com.monitoring.cellshark.data

enum class EndpointType {
    PING, GET
}

enum class ButtonNames {
    METRICS, ENDPOINTS
}

enum class ConnectionInterface {
    CELLULAR, WIFI, OFFLINE, ETHERNET
}

object Constants {
    const val PARENT = 0
    const val CHILD = 1
}

var CHECKER_FINISHED = false
var SERVICE_RUNNING = false
var global_list_temp = mutableListOf<Endpoint>()
const val PREFS_SERIAL              = "PreferenceSerial"
const val PREFS_SERIAL_KEY          = "PreferenceSerialKey"

const val REQUEST_CODE              = 101
const val CHANNEL_ID                = "com.monitoring.cellshark"
const val APP_NAME                  = "CellShark"

val endpoints = arrayOf(
    Endpoint(arrayOf("52.223.52.124", "35.71.178.142"), "Backup API GA", EndpointType.PING, "Backup API Traffic"),
    Endpoint(arrayOf("52.223.48.71", "35.71.150.142"), "Backup Websocket GA", EndpointType.PING, "Backup Websocket Traffic"),
    Endpoint(arrayOf("https://cellshark.augmedix.com:1200"), "S.M.A.R.T.", EndpointType.GET, "Connection Health Metrics"),
    Endpoint(arrayOf("https://cloudservice.augmedix.com"), "Cloud Service Wrapper", EndpointType.GET, "Wrap Cloud URL which are accessible from SCP or DocApp with this URL"),
    Endpoint(arrayOf("https://ee-portal.augmedix.com"), "Augmedix Customer Portal", EndpointType.GET, "Customer Login Portal"),
    Endpoint(arrayOf("https://uploader.augmedix.com"),"File Server", EndpointType.GET, "Handles file upload process for both DocApp and SCP"),
    Endpoint(arrayOf("34.110.173.3"), "GCP Front LB", EndpointType.PING, "Frontend App"),
    Endpoint(arrayOf("34.120.86.173", "34.160.187.231", "35.227.219.48"), "GCP LB and App IPs", EndpointType.PING, "N/A"),
    Endpoint(arrayOf("https://lynx.augmedix.com"), "Lynx App", EndpointType.GET, "N/A"),
    Endpoint(arrayOf("https://api.logentries.com", "https://eu.ops.insight.rapid7.com"), "Logentries", EndpointType.GET),
    Endpoint(arrayOf("https://mcu3.augmedix.com:9090/v1/ping/", "https://mcu4.augmedix.com:9090/v1/ping", "https://mcu6.augmedix.com:9090/v1/ping"), "MCUs", EndpointType.GET, "Streaming endpoints"),
    Endpoint(arrayOf("https://messaging.augmedix.com"), "Messaging Platform", EndpointType.GET, "App messaging service"),
    Endpoint(arrayOf("52.223.21.77", "35.71.161.128"), "Microservice Global Accelerator", EndpointType.PING),
    Endpoint( arrayOf("https://cn705.awmdm.com", "https://ds705.awmdm.com","https://awcm705.awmdm.com", "https://na1.data.vmwservices.com"),
                    "Airwatch MDM Suite", EndpointType.GET, "Airwatch MDM Suite"),

    Endpoint(arrayOf("https://play.google.com", "https://www.android.com", "https://www.google-analytics.com",
    "https://dl.google.com", "https://www.googleapis.com", "https://dl-ssl.google.com", "https://dl.google.com", "https://www.google.com/generate_204"), "Airwatch Google Endpoints", EndpointType.GET, "Google Endpoints for Airwatch functionality"),

    Endpoint(arrayOf("https://apps.apple.com", "https://augmedix.jamfcloud.com/"), "Augmedix JAMF MDM", EndpointType.GET),
    Endpoint(arrayOf("https://www.ntppool.org/en/"), "NTP", EndpointType.GET),
    Endpoint(arrayOf("15.197.218.73", "3.33.251.133"), "Websocket in Global Accelerator", EndpointType.PING)
)
