package com.fit.feast.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fit.feast.R
import com.fit.feast.databinding.FragmentHomeScreenBinding
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
                    findNavController().navigate(R.id.action_workoutFragment_to_workoutSearchFragment)
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
