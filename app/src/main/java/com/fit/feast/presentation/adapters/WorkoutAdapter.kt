package com.fit.feast.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.databinding.WorkoutCardBinding

class WorkoutAdapter(private val onCardClick: (Exercises) -> Unit) :
    PagingDataAdapter<Exercises, WorkoutAdapter.ExerciseViewHolder>(ExerciseDiffCallback) {
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)!!
        holder.binding.apply {
            cardView.setOnClickListener {
                onCardClick(exercise)
                cardView.isEnabled  = false
            }
            exerciseName.text = exercise.name
            equipment.text = "Equipment: ${exercise.equipment}"
            bodyPart.text = "Body Part: ${exercise.bodyPart}"
            targetMuscle.text = "Muscle: ${exercise.target}"
            Glide.with(root.context).asGif().load(exercise.gifUrl).into(exerciseImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = WorkoutCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExerciseViewHolder(binding)
    }

    class ExerciseViewHolder (val binding: WorkoutCardBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        val ExerciseDiffCallback = object : DiffUtil.ItemCallback<Exercises>() {
            override fun areItemsTheSame(oldItem: Exercises, newItem: Exercises): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Exercises, newItem: Exercises): Boolean {
                return oldItem == newItem
            }
        }
    }

}
