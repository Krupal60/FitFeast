package com.fit.feast.presentation.screens

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
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.databinding.FragmentEquipmentWorkoutBinding
import com.fit.feast.presentation.adapters.WorkoutAdapter
import com.fit.feast.presentation.adapters.footer.LoadStateFooterAdapter
import com.fit.feast.presentation.viewmodel.EquipmentWorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EquipmentWorkoutFragment : Fragment() {

    private var _binding: FragmentEquipmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EquipmentWorkoutViewModel by viewModels()
    private lateinit var adapter : WorkoutAdapter
    private val args : EquipmentWorkoutFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData(args.equipment)
        _binding = FragmentEquipmentWorkoutBinding.inflate(inflater, container, false)
        adapter = WorkoutAdapter{
            val action = EquipmentWorkoutFragmentDirections.actionEquipmentWorkoutFragmentToWorkOutDetailFragment(it)
            findNavController().navigate(action)
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
            title.text = "By ${args.equipment}"
            backButton.setOnClickListener {
                findNavController().popBackStack()
                backButton.isEnabled = false
            }
            adapter.addLoadStateListener { loadState ->
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
                adapter.retry()
            }

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = adapter.withLoadStateFooter(LoadStateFooterAdapter{
                adapter.retry()
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}