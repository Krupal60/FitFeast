package com.fit.feast.presentation.screens

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fit.feast.R
import com.fit.feast.databinding.FragmentByTargetsBinding
import com.fit.feast.presentation.adapters.ByTargetsAdapter
import com.fit.feast.presentation.viewmodel.ByTargetsViewModel


class ByTargetsFragment : Fragment() {

    private var _binding: FragmentByTargetsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ByTargetsViewModel by viewModels()
    private val adapter = ByTargetsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentByTargetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
                backButton.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}