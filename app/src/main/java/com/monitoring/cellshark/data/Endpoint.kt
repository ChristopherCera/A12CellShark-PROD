package com.monitoring.cellshark.data

import kotlin.properties.Delegates

class Endpoint(addresses: Array<String>, name: String, type: EndpointType, description: String) {

    var endpointName: String = name
    var endpoints: Array<String> = addresses
    var type: EndpointType = type
    var description: String = description
    private var result: MutableList<EndpointResult> = mutableListOf()

    fun addResult(r: EndpointResult) {
        result.add(r)
    }

    fun getPair(): MutableList<EndpointResult> {
        return result
    }

}

data class EndpointResult(val address: String, val result: Boolean)