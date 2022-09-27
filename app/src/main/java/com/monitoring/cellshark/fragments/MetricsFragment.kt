package com.monitoring.cellshark.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.monitoring.cellshark.R
import com.monitoring.cellshark.databinding.MetricsFragmentBinding

class MetricsFragment: Fragment(R.layout.metrics_fragment) {

    private var _binding: MetricsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = MetricsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("TAG", "Metrics Fragment")
        binding.serialContainer.setOnLongClickListener {
            Log.d("TAG", "TEST")
            true
        }


        super.onViewCreated(view, savedInstanceState)
    }

}