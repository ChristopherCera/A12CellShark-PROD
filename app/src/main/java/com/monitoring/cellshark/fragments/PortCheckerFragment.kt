package com.monitoring.cellshark.fragments

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.monitoring.cellshark.R
import com.monitoring.cellshark.data.Endpoint
import com.monitoring.cellshark.data.EndpointType
import com.monitoring.cellshark.data.MyRecyclerViewAdapter
import com.monitoring.cellshark.data.*
import com.monitoring.cellshark.databinding.PortCheckerFragmentBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class PortCheckerFragment: Fragment(R.layout.port_checker_fragment) {

    private var _binding: PortCheckerFragmentBinding? = null
    private val binding get() = _binding!!
    private var mobileList = ArrayList<String>()
    private var endpointResultList: MutableList<Endpoint> = mutableListOf()
    private val startHeight = 150
    private val endHeight = 900
    private var isExpanded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = PortCheckerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        global_list_temp = endpointResultList
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView = binding.recyclerViewItems

        binding.runCheckerButton.setOnClickListener {

            binding.runCheckerButton.text = "Running Port Checker..."
            binding.runCheckerButton.isClickable = false
            binding.runCheckerButton.setBackgroundColor(Color.GRAY)

            GlobalScope.launch(IO) {
                if (CHECKER_FINISHED) {
                    endpointResultList.clear()
                }
                runEndpointChecker()
//                endpointResultList.forEach {
//                    Log.d("endpoint", "AFTER -- Address: ${it.endpointName}\tSize: ${it.result.size}")
//                }

                withContext(Dispatchers.Main) {
                    val customAdapter = MyRecyclerViewAdapter(endpointResultList)
                    val layoutManager = LinearLayoutManager(context)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = customAdapter
                    binding.runCheckerButton.text = "Port Checker Done"
                    CHECKER_FINISHED = true
                }
            }
        }



        if (CHECKER_FINISHED) {
            binding.runCheckerButton.text = "Re-run Endpoint Checker"
            endpointResultList = global_list_temp
            Log.d("onViewCreated", "onViewCreated(), temp size: ${global_list_temp.size}")
        }

        Log.d("onViewCreated", "onViewCreated(), epr size: ${endpointResultList.size}")

        val customAdapter = MyRecyclerViewAdapter(endpointResultList)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
        super.onViewCreated(view, savedInstanceState)
    }

    private suspend fun runEndpointChecker(): MutableList<Endpoint>? = withContext(IO) {
        //testforgithub
        endpoints.forEach { ep ->

            if (ep.epType == EndpointType.GET) checkEPGet(ep)
            else {
                checkEPPing(ep)
             }
        }
        return@withContext null
    }

    private fun slideView(view: View, currentHeight: Int, newHeight: Int) {
        val slideAnimator = ValueAnimator.ofInt(currentHeight, newHeight).setDuration(500)
        slideAnimator.addUpdateListener { animation1 ->
            val value: Int = animation1.animatedValue as Int
            view.layoutParams.height = value
            view.requestLayout()
        }
        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(slideAnimator)
        animationSet.start()
    }

    private fun checkEPGet(ep: Endpoint) {

        if (ep.endpoints.size == 1) {
            val epObject = Endpoint(ep.endpoints, ep.endpointName, ep.epType, ep.description)
            val httpASync = ep.endpoints[0].httpGet().timeout(500).timeoutRead(500)
                .responseString { _, _, result ->
                    result.success {}
                    result.failure {}
                }
            val result = httpASync.join()
            epObject.updateParentResult(result.isSuccessful)
            epObject.addResult(EndpointResult(ep.endpoints[0], result.isSuccessful))
            endpointResultList.add(epObject)
            Log.d("endpoint", "Address: ${ep.endpoints[0]}\tResult:${result.isSuccessful}\tCode: ${result.statusCode}")
        } else {
            val epObject: Endpoint = Endpoint(ep.endpoints, ep.endpointName, ep.epType, ep.description)
            ep.endpoints.forEach {

                val httpASync = it.httpGet().timeout(500).timeoutRead(500)
                    .responseString { _, _, result ->
                        result.success {}
                        result.failure {}
                    }
                val result = httpASync.join()

                if (it == "https://augmedix.jamfcloud.com/") {
                    if (result.statusCode == 401) {
                        epObject.updateParentResult(true)
                        epObject.addResult(EndpointResult(it, true))
                    }
                } else {
                    epObject.updateParentResult(result.isSuccessful)
                    epObject.addResult(EndpointResult(it, result.isSuccessful))
                }

                Log.d("endpoint", "Address: ${it}\tResult:${result.isSuccessful}\tCode: ${result.statusCode}")
            }

            endpointResultList.add(epObject)

        }
    }

    private fun checkEPPing(ep: Endpoint) {

        val runtime: Runtime = Runtime.getRuntime()
        val pingCommand = "/system/bin/ping -c 1 "

        if (ep.endpoints.size == 1) {
            val epObject = Endpoint(ep.endpoints, ep.endpointName, ep.epType, ep.description)
            try {

                val runCommand = runtime.exec(pingCommand + epObject.endpoints[0])
                val response = runCommand.waitFor()

                epObject.updateParentResult(response == 0)
                epObject.addResult(EndpointResult(epObject.endpoints[0], response == 0))
                endpointResultList.add(epObject)

            } catch (E: Exception) {
                Log.e("CheckPingError", "**Error while running ping command for address ${ep.endpoints[0]}**\n\n" + E.printStackTrace())
            }
        } else {
            val epObject = Endpoint(ep.endpoints, ep.endpointName, ep.epType, ep.description)
            epObject.endpoints.forEach { address ->
                val command = runtime.exec(pingCommand + address)
                val response = command.waitFor()
                epObject.updateParentResult(response == 0)
                epObject.addResult(EndpointResult(address, response == 0))
            }
            endpointResultList.add(epObject)
        }



    }

    override fun onResume() {
        super.onResume()
        if (CHECKER_FINISHED) { endpointResultList = global_list_temp }
    }


}