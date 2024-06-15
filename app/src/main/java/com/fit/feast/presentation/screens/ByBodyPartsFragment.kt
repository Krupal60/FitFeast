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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.feast.R
import com.fit.feast.databinding.FragmentByBodyPartsBinding
import com.fit.feast.presentation.adapters.ByBodyPartsAdapter
import com.fit.feast.presentation.viewmodel.ByBodyPartsViewModel
import com.fit.feast.util.RequestState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ByBodyPartsFragment : Fragment() {

    private var _binding: FragmentByBodyPartsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ByBodyPartsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getBodyPartsList()
        _binding = FragmentByBodyPartsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
                backButton.isEnabled = false
            }
            recyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            recyclerView.itemAnimator = DefaultItemAnimator()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.bodyParts.collect{ requestState ->
                    when(requestState){
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
                            recyclerView.adapter = ByBodyPartsAdapter(requestState.data){
                                val action = ByBodyPartsFragmentDirections.actionByBodyPartsFragmentToBodyPartWorkoutFragment(it)
                                findNavController().navigate(action)
                            }
                            recyclerView.visibility = View.VISIBLE

                        }
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}