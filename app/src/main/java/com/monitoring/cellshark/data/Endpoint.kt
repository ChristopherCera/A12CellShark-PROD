package com.monitoring.cellshark.data

class Endpoint(addresses: Array<String>, name: String, type: EndpointType, description: String = "N/A") {

    var endpointName: String = name
    var type: Int = Constants.PARENT
    var endpoints: Array<String> = addresses
    var epType: EndpointType = type
    var description: String = description
    var isExpanded: Boolean = false
    var result: MutableList<EndpointResult> = mutableListOf()
    var parentResult: Boolean? = null

    fun addResult(r: EndpointResult) {
        result.add(r)
    }

    fun getPair(): MutableList<EndpointResult> {
        return result
    }

    fun updateParentResult(result: Boolean) {
        parentResult = result
    }

    constructor(addresses: Array<String>, name: String, type: EndpointType, description: String, list: MutableList<EndpointResult>) :
            this(addresses, name, type, description) {
        result = list
    }

}

data class EndpointResult(val address: String, val result: Boolean)