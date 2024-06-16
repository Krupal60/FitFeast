package com.fit.feast.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.fit.feast.databinding.FragmentByEquipmentsBinding
import com.fit.feast.presentation.adapters.ByItemAdapter
import com.fit.feast.presentation.viewmodel.ByEquipmentsViewModel
import com.fit.feast.util.RequestState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
            backButton.setOnClickListener {
                findNavController().popBackStack()
                backButton.isEnabled = false
            }
            recyclerView.layoutManager = GridLayoutManager(requireContext(),2,
                GridLayoutManager.VERTICAL,false)
            recyclerView.itemAnimator = DefaultItemAnimator()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.data.collect{ requestState ->
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
                            recyclerView.adapter = ByItemAdapter(requestState.data){
                                val action = ByEquipmentsFragmentDirections.actionByEquipmentsFragmentToEquipmentWorkoutFragment(it)
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