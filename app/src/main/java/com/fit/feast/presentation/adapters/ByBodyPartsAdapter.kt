package com.fit.feast.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.feast.R
import com.fit.feast.databinding.ByBodyPartsListBinding

class ByBodyPartsAdapter (private val byBodyPartsList: List<String>): RecyclerView.Adapter<ByBodyPartsAdapter.ViewHolder>() {
     class ViewHolder(val binding : ByBodyPartsListBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ByBodyPartsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return byBodyPartsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val byBodyParts = byBodyPartsList[position]
        val id = when (byBodyParts) {
            "body" -> {
                R.drawable.human_body
            }

            "body parts" -> {
                R.drawable.human_body_parts
            }

            "equipment" -> {
                R.drawable.pilates
            }

            "back" -> {
                R.drawable.human_back
            }

            "cardio" -> {
                R.drawable.human_cardio
            }

            "chest" -> {
                R.drawable.human_chest_male
            }

            "lower arms" -> {
                R.drawable.human_arm
            }

            "lower legs" -> {
                R.drawable.human_lower_leg
            }

            "neck" -> {
                R.drawable.human_neck
            }

            "shoulders" -> {
                R.drawable.human_shoulder
            }

            "upper arms" -> {
                R.drawable.human_bicep
            }

            "upper legs" -> {
                R.drawable.human_knee
            }

            "waist" -> {
                R.drawable.human_waist
            }

            else -> {
                R.drawable.human_workout
            }
        }

            holder.binding.bodyPart.text = byBodyParts
            holder.binding.exerciseImage.setImageResource( id)
        }


}
