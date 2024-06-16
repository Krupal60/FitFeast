package com.fit.feast.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.feast.databinding.ListLayoutBinding

class ByItemAdapter(private val items: List<String>, private val cardClick: (String) -> Unit) : RecyclerView.Adapter<ByItemAdapter.ViewHolder>(){
    class ViewHolder(val binding: ListLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
            holder.binding.name.text = data
            holder.binding.cardView.setOnClickListener {
                cardClick(data)
                holder.binding.cardView.isEnabled = false
            }

    }

}
