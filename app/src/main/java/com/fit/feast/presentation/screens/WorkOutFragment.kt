package com.fit.feast.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fit.feast.R
import com.fit.feast.databinding.FragmentHomeScreenBinding
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkOutFragment : Fragment(), OnClickListener {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner, // Use viewLifecycleOwner for proper lifecycle handling
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (searchView.currentTransitionState == SearchView.TransitionState.SHOWN) {
                            searchView.hide()
                            allWorkout.visibility = View.VISIBLE
                            workoutByParts.visibility = View.VISIBLE
                            workoutByEquipment.visibility = View.VISIBLE
                        }
                        if (searchView.currentTransitionState == SearchView.TransitionState.HIDDEN) {
                            remove()
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            )
            searchView.addTransitionListener { searchView, transitionState, transitionState2 ->
                if (transitionState == SearchView.TransitionState.HIDING) {
                    allWorkout.visibility = View.VISIBLE
                    workoutByParts.visibility = View.VISIBLE
                    workoutByEquipment.visibility = View.VISIBLE
                }
            }
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    false
                }
            searchBar.setOnClickListener(this@WorkOutFragment)
            allWorkout.setOnClickListener(this@WorkOutFragment)
            workoutByParts.setOnClickListener(this@WorkOutFragment)
            workoutByTarget.setOnClickListener(this@WorkOutFragment)
            workoutByEquipment.setOnClickListener(this@WorkOutFragment)
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v?.id) {
                searchBar.id -> {
                    allWorkout.visibility = View.GONE
                    workoutByParts.visibility = View.GONE
                    workoutByEquipment.visibility = View.GONE
                    searchView.show()
                }

                allWorkout.id -> {
                    findNavController().navigate(R.id.action_workoutFragment_to_allWorkoutFragment)
                }

                workoutByParts.id -> {
                    findNavController().navigate(R.id.action_workoutFragment_to_byBodyPartsFragment)

                }

                workoutByTarget.id -> {
                    findNavController().navigate(R.id.action_workoutFragment_to_byTargetsFragment)

                }

                workoutByEquipment.id -> {
                    findNavController().navigate(R.id.action_workoutFragment_to_byEquipmentsFragment)
                }
            }
        }

    }

}
