package com.monitoring.cellshark.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.monitoring.cellshark.R
import com.monitoring.cellshark.Utility
import com.monitoring.cellshark.databinding.HomeBinding

class HomeFragment: Fragment(R.layout.home) {

    private var _binding: HomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = HomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("TAG", "Metrics Fragment")
        binding.serialContainer.setOnLongClickListener {
            Log.d("TAG", "TEST")
            binding.deviceSn.text = "Unable to Retrieve Device Serial"
            true
        }

        val networkInterface = Utility.getActiveConnectionInterface(context)
        binding.activeInterfaceDynamic.text = networkInterface.name

        super.onViewCreated(view, savedInstanceState)
    }


}