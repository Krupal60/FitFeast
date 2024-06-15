package com.fit.feast.presentation.screens

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.databinding.FragmentTargetMuscleWorkoutBinding
import com.fit.feast.presentation.adapters.TargetMuscleFooterAdapter
import com.fit.feast.presentation.adapters.TargetMuscleWorkoutAdapter
import com.fit.feast.presentation.viewmodel.TargetMuscleWorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TargetMuscleWorkoutFragment : Fragment() {

    private var _binding: FragmentTargetMuscleWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TargetMuscleWorkoutViewModel by viewModels()
    private lateinit var adapter: TargetMuscleWorkoutAdapter
    private val args : TargetMuscleWorkoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData(args.targetMuscle)
        _binding = FragmentTargetMuscleWorkoutBinding.inflate(inflater, container, false)
        adapter = TargetMuscleWorkoutAdapter {

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.data.collectLatest { value: Flow<PagingData<Exercises>> ->
                value.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        binding.apply {
            title.text  = "Workout By ${args.targetMuscle} Muscle"
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = adapter.withLoadStateFooter(TargetMuscleFooterAdapter{
                adapter.retry()
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}