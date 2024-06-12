package com.fit.feast.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.fit.feast.databinding.FragmentAllWorkoutBinding
import com.fit.feast.presentation.adapters.AllWorkoutAdapter
import com.fit.feast.presentation.viewmodel.AllWorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllWorkoutFragment : Fragment() {

    private var _binding: FragmentAllWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AllWorkoutViewModel by viewModels()
    private lateinit var workoutAdapter: AllWorkoutAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getWorkout()
        workoutAdapter = AllWorkoutAdapter()
        _binding = FragmentAllWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            viewModel.allWorkout.observe(viewLifecycleOwner) {
                viewLifecycleOwner.lifecycleScope.launch {
                    workoutAdapter.submitData(it)
                }
            }



        binding.apply {

            backButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = workoutAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}