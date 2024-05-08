package com.example.myapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.FragmentExtraBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //Required for field injection
class ExtraFragment: Fragment() {

    lateinit var binding: FragmentExtraBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExtraBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ExtraFragment"
        fun newInstance(): ExtraFragment {
            val args = Bundle()
            val fragment = ExtraFragment()
            fragment.arguments = args
            return fragment
        }
    }

}