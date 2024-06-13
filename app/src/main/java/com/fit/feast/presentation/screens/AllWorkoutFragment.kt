package com.fit.feast.presentation.screens

import com.fit.feast.presentation.adapters.WorkoutLoadStateAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.fit.feast.R
import com.fit.feast.databinding.FragmentAllWorkoutBinding
import com.fit.feast.presentation.adapters.AllWorkoutAdapter
import com.fit.feast.presentation.viewmodel.AllWorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
        _binding = FragmentAllWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workoutAdapter = AllWorkoutAdapter {
           findNavController().navigate(R.id.action_allWorkoutFragment_to_workOutDetailFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allWorkout.collectLatest { pagingDataFlow ->
                pagingDataFlow.collectLatest {
                    workoutAdapter.submitData(it)
                }
            }
        }


    binding.apply{
        workoutAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    retry.visibility = View.GONE
                    errorText.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    retry.visibility = View.GONE
                    errorText.visibility = View.GONE
                }

                is LoadState.Error -> {
                    progressBar.visibility = View.GONE
                    retry.visibility = View.VISIBLE
                    errorText.visibility = View.VISIBLE
                }
            }
        }
        retry.setOnClickListener {
            workoutAdapter.retry()
        }
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter =    workoutAdapter.withLoadStateFooter(WorkoutLoadStateAdapter {
            workoutAdapter.retry()
        })
    }

}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
}