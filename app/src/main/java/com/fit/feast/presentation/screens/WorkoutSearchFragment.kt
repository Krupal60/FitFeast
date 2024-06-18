package com.fit.feast.presentation.screens

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.fit.feast.databinding.FragmentWorkoutSearchBinding
import com.fit.feast.presentation.adapters.WorkoutAdapter
import com.fit.feast.presentation.adapters.footer.LoadStateFooterAdapter
import com.fit.feast.presentation.viewmodel.SearchWorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutSearchFragment : Fragment() {
    private var _binding: FragmentWorkoutSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchWorkoutViewModel by viewModels()
    private lateinit var workoutAdapter: WorkoutAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.apply {
            if (editText.text!!.isNotEmpty()){
                viewModel.getData(editText.text.toString())
                progressBar.visibility = View.VISIBLE
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workoutAdapter = WorkoutAdapter {
            val action = WorkoutSearchFragmentDirections.actionWorkoutSearchFragmentToWorkOutDetailFragment(it)
            findNavController().navigate(action)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.data.collectLatest { pagingDataFlow ->
                pagingDataFlow.collectLatest {
                    workoutAdapter.submitData(it)
                }
            }
        }


        binding.apply{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                editText.isFocusedByDefault = true
            }

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
            serachButton.setOnClickListener {
                if (editText.text.toString().isNotEmpty() && editText.isFocused){
                    viewModel.getData(editText.text.toString())
                    progressBar.visibility = View.VISIBLE
                    editText.clearFocus()
                }
            }

            editText.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
                viewModel.getData(editText.text.toString())
                progressBar.visibility = View.VISIBLE
                editText.clearFocus()
                true
            }



            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter =    workoutAdapter.withLoadStateFooter(LoadStateFooterAdapter {
                workoutAdapter.retry()
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}