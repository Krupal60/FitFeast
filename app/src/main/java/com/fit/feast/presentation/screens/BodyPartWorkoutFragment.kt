package com.fit.feast.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.fit.feast.databinding.FragmentBodyPartWorkoutBinding
import com.fit.feast.presentation.adapters.WorkoutAdapter
import com.fit.feast.presentation.adapters.footer.LoadStateFooterAdapter
import com.fit.feast.presentation.viewmodel.BodyPartWorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BodyPartWorkoutFragment : Fragment() {

    private var _binding: FragmentBodyPartWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BodyPartWorkoutViewModel by viewModels()
    private lateinit var workoutAdapter: WorkoutAdapter
    private val args : BodyPartWorkoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBodyPartWorkoutBinding.inflate(inflater, container, false)
        viewModel.getData(args.bodyPart)
        workoutAdapter = WorkoutAdapter{
            val action = BodyPartWorkoutFragmentDirections.actionBodyPartWorkoutFragmentToWorkOutDetailFragment(it)
            findNavController().navigate(action)
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.data.collectLatest { pagingDataFlow ->
                pagingDataFlow.collectLatest {
                    workoutAdapter.submitData(it)
                }
            }
        }

        binding.apply {
            title.text = "Workouts by ${args.bodyPart} part"

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
                backButton.isEnabled = false
            }

            recyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = workoutAdapter.withLoadStateFooter(
                LoadStateFooterAdapter{
                    workoutAdapter.retry()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}