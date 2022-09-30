package com.monitoring.cellshark.fragments

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.monitoring.cellshark.R
import com.monitoring.cellshark.data.MyRecyclerViewAdapter
import com.monitoring.cellshark.databinding.PortCheckerFragmentBinding
import com.monitoring.cellshark.serviceEndpointsGET

class PortCheckerFragment: Fragment(R.layout.port_checker_fragment) {

    private var _binding: PortCheckerFragmentBinding? = null
    private val binding get() = _binding!!
    private var mobileList = ArrayList<String>()
    private val interpolator = OvershootInterpolator()
    private val startHeight = 150
    private val endHeight = 900
    private var isExpanded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = PortCheckerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        val recyclerView = binding.recyclerViewItems
        binding.expandListButton.setOnClickListener {
//            if (!isExpanded) {
//                TransitionManager.beginDelayedTransition(recyclerView, TransitionSet().addTransition(ChangeBounds()))
//                val params = recyclerView.layoutParams
//                params.height = endHeight
//                recyclerView.layoutParams = params
//                isExpanded = true
//            } else {
//                TransitionManager.beginDelayedTransition(recyclerView, TransitionSet().addTransition(ChangeBounds()))
//                val params = recyclerView.layoutParams
//                params.height = startHeight
//                recyclerView.layoutParams = params
//                isExpanded = false
//            }
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

        getEndpoints(mobileList)
        val customAdapter = MyRecyclerViewAdapter(mobileList)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
//        customAdapter.notifyDataSetChanged()



        super.onViewCreated(view, savedInstanceState)
    }

    private fun getEndpoints(list: ArrayList<String>) {
        serviceEndpointsGET.forEach {
            list.add(it)
        }
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


}