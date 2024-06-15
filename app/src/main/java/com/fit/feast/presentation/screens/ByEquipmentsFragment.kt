package com.fit.feast.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fit.feast.databinding.FragmentByEquipmentsBinding
import com.fit.feast.presentation.viewmodel.ByEquipmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ByEquipmentsFragment : Fragment() {

    private var _binding: FragmentByEquipmentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ByEquipmentsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData()
        _binding = FragmentByEquipmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}