package com.fit.feast.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.databinding.WorkoutCardBinding

class AllWorkoutAdapter :
    PagingDataAdapter<Exercises, AllWorkoutAdapter.WorkoutViewHolder>(WORKOUT_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = WorkoutCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = getItem(position)

        Log.d("Workout1", workout.toString())
        holder.bind(workout!!)
    }

    class WorkoutViewHolder(private val binding: WorkoutCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Exercises) {
            binding.apply {
                exerciseName.text = item.name
                equipment.text = "Equipment: ${item.equipment}"
                bodyPart.text = "Body Part: ${item.bodyPart}"
                targetMuscle.text = "Muscle: ${item.target}"
                Glide.with(root.context).asGif().load(item.gifUrl).into(exerciseImage)

            }
        }

    }

    companion object {
        private val WORKOUT_COMPARATOR = object : DiffUtil.ItemCallback<Exercises>() {
            override fun areItemsTheSame(oldItem: Exercises, newItem: Exercises): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Exercises, newItem: Exercises): Boolean {
                return oldItem == newItem
            }
        }
    }


}
