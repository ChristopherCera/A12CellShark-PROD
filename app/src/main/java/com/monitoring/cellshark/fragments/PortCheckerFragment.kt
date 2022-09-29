package com.monitoring.cellshark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = PortCheckerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerViewItems
        getEndpoints(mobileList)
        val customAdapter = MyRecyclerViewAdapter(mobileList)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
        customAdapter.notifyDataSetChanged()



        super.onViewCreated(view, savedInstanceState)
    }

    private fun getEndpoints(list: ArrayList<String>) {
        serviceEndpointsGET.forEach {
            list.add(it)
        }
    }


}