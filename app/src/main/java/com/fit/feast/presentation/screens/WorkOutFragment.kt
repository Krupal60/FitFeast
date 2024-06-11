package com.fit.feast.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.fit.feast.databinding.FragmentHomeScreenBinding
import com.google.android.material.search.SearchView


class WorkOutFragment : Fragment() {

    var _binding : FragmentHomeScreenBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater,container,false)
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
                  if (transitionState == SearchView.TransitionState.HIDING){
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
            searchBar.setOnClickListener {
                allWorkout.visibility = View.GONE
                workoutByParts.visibility = View.GONE
                workoutByEquipment.visibility = View.GONE
               searchView.show()
            }}
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
