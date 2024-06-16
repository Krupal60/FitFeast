package com.fit.feast.presentation.screens

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.feast.databinding.FragmentByTargetsBinding
import com.fit.feast.presentation.adapters.ByItemAdapter
import com.fit.feast.presentation.viewmodel.ByTargetsViewModel
import com.fit.feast.util.RequestState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ByTargetsFragment : Fragment() {

    private var _binding: FragmentByTargetsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ByTargetsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData()
        _binding = FragmentByTargetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.itemAnimator = DefaultItemAnimator()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.data.collectLatest { value: RequestState<List<String>> ->
                    when(value){
                        is RequestState.Error -> {
                            recyclerView.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            errorText.visibility = View.VISIBLE
                        }
                        RequestState.Idle -> {}
                        RequestState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is RequestState.Success -> {
                            progressBar.visibility = View.GONE
                            errorText.visibility = View.GONE
                            recyclerView.adapter = ByItemAdapter(value.data){
                                val action = ByTargetsFragmentDirections.actionByTargetsFragmentToTargetMuscleWorkoutFragment(it)
                                findNavController().navigate(action)
                            }
                            recyclerView.visibility = View.VISIBLE

                        }
                    }

                }
            }


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