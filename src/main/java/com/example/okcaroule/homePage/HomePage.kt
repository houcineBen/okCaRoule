package com.example.okcaroule

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.okcaroule.databinding.FragmentHomePageBinding


class HomePage : Fragment() {
   lateinit var binding: FragmentHomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        binding.commandBtn.setOnClickListener {
            findNavController().navigate(HomePageDirections.actionHomePageToCommandsFragment())
        }
        binding.locationBtn.setOnClickListener {
            findNavController().navigate(HomePageDirections.actionHomePageToLocationFragment())
        }

        return binding.root
    }

}