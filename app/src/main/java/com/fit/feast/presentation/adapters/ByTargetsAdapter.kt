package com.fit.feast.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.feast.databinding.ByTargetMuscleListBinding

class ByTargetsAdapter(private val byTargets: List<String>, private val cardClick: (String) -> Unit) : RecyclerView.Adapter<ByTargetsAdapter.ViewHolder>(){
    class ViewHolder(val binding: ByTargetMuscleListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ByTargetMuscleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return byTargets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = byTargets[position]
            holder.binding.targetMuscle.text = data
            holder.binding.cardView.setOnClickListener {
                cardClick(data)
                holder.binding.cardView.isEnabled = false
            }

    }

}
