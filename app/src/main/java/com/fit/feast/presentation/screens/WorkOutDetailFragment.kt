package com.fit.feast.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fit.feast.R
import com.fit.feast.databinding.FragmentWorkOutDetailBinding

class WorkOutDetailFragment : Fragment() {

    private var _binding: FragmentWorkOutDetailBinding? = null
    private val binding get() = _binding!!
    private val args: WorkOutDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkOutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val exercise = args.exercises!!
            nameTextView.text = "Name : ${exercise.name}"
            bodyPartTextView.text = "Body Part: ${exercise.bodyPart}"
            equipmentTextView.text = "Equipment: ${exercise.equipment}"
            targetTextView.text = "Target: ${exercise.target}"
            secondaryMuscleText.text = "Secondary Muscles: ${exercise.secondaryMuscles?.joinToString(", ") ?: "None"}"
            instructionsTextView.text = exercise.instructions?.joinToString("\n") ?: "No instructions available"
            Glide.with(requireContext()).asGif().load(exercise.gifUrl).into(gifImageView)

            backButton.setOnClickListener {
                findNavController().popBackStack()
                backButton.isEnabled = false
            }
            showInstructionsCardView.setOnClickListener {
                if ( instructionsTextView.isVisible){
                    instructionsTextView.visibility = View.GONE
                    expandInstructionsButton.setImageResource(R.drawable.arrow_down)
                    return@setOnClickListener
                }
                if ( !instructionsTextView.isVisible){
                    instructionsTextView.visibility = View.VISIBLE
                    expandInstructionsButton.setImageResource(R.drawable.arrow_up)
                    return@setOnClickListener
                }
            }

            expandInstructionsButton.setOnClickListener {
                if ( instructionsTextView.isVisible){
                    instructionsTextView.visibility = View.GONE
                    expandInstructionsButton.setImageResource(R.drawable.arrow_down)
                    return@setOnClickListener
                }
                if ( !instructionsTextView.isVisible){
                    instructionsTextView.visibility = View.VISIBLE
                    expandInstructionsButton.setImageResource(R.drawable.arrow_up)
                    return@setOnClickListener
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}