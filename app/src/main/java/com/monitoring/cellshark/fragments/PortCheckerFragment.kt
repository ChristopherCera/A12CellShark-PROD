package com.monitoring.cellshark.fragments

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
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
    private lateinit var endpointList: MutableList<Endpoint>
    private val startHeight = 150
    private val endHeight = 900
    private var isExpanded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = PortCheckerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        val recyclerView = binding.recyclerViewItems
        binding.expandListButton.setOnClickListener {
            if (!isExpanded) {
                slideView(recyclerView, startHeight, endHeight)
                binding.listExpandTv.text = "Click to minimize list"
                isExpanded = true
            } else {
                slideView(recyclerView, endHeight, startHeight)
                binding.listExpandTv.text = "Click to expand endpoint list"
                isExpanded = false
            }
        }

        binding.runCheckerButton.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
//                checkEndpointConnectivity( "www.google.com")
                endpoints.forEach {
                    checkEndpointConnectivity(it)
                }

            }


        }

        getEndpoints(mobileList)
        val customAdapter = MyRecyclerViewAdapter(mobileList)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getEndpoints(list: ArrayList<String>) {
        endpoints.forEach {
//            list.add(it)
        }
    }

    private suspend fun runEndpointChecker(): MutableList<Endpoint>? = withContext(IO) {

//        endpointHTTPGet.forEach {
//            endpointList.add(Endpoint(it, EndpointType.GET))
//        }




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

    private fun checkEndpointConnectivity(ep: Endpoint) {

        if (ep.endpoints.size > 1) {

        } else {
            val httpASync = ep.endpoints[0].httpGet().timeout(500).timeoutRead(500)
                .responseString { _, _, result ->
                    result.success {}
                    result.failure {}
                }
            val result = httpASync.join()

            ep.addResult(EndpointResult(ep.endpoints[0], result.isSuccessful))

            Log.d("endpoint", "Address: ${ep.endpoints[0]}\tResult:${result.isSuccessful}\tCode: ${result.statusCode}")
        }

    }


}