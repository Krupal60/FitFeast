package com.fit.feast.presentation.screens

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.fit.feast.presentation.viewmodel.BodyPartWorkoutViewModel
import com.fit.feast.R
import com.fit.feast.databinding.FragmentBodyPartWorkoutBinding
import com.fit.feast.presentation.adapters.BodyPartWorkoutAdapter
import com.fit.feast.presentation.adapters.BodyPartWorkoutFooterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BodyPartWorkoutFragment : Fragment() {

    private var _binding: FragmentBodyPartWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BodyPartWorkoutViewModel by viewModels()
    private lateinit var bodyPartWorkoutAdapter: BodyPartWorkoutAdapter
    private val args : BodyPartWorkoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBodyPartWorkoutBinding.inflate(inflater, container, false)
        Log.d("TAG", "onViewCreated: ${args.bodyPart}")
        viewModel.getData(args.bodyPart)
        bodyPartWorkoutAdapter = BodyPartWorkoutAdapter{
            findNavController().navigate(R.id.action_bodyPartWorkoutFragment_to_workOutDetailFragment)
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.data.collectLatest { pagingDataFlow ->
                pagingDataFlow.collectLatest {
                    bodyPartWorkoutAdapter.submitData(it)
                }
            }
        }

        binding.apply {
            title.text = "Workouts by ${args.bodyPart} part"

            bodyPartWorkoutAdapter.addLoadStateListener { loadState ->
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
                bodyPartWorkoutAdapter.retry()
            }
            backButton.setOnClickListener {
                findNavController().popBackStack()
                backButton.isEnabled = false
            }

            recyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = bodyPartWorkoutAdapter.withLoadStateFooter(
                BodyPartWorkoutFooterAdapter{
                    bodyPartWorkoutAdapter.retry()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}