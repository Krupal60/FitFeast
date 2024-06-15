package com.fit.feast.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fit.feast.databinding.LoadStateScreenBinding

class BodyPartWorkoutFooterAdapter(private val onRetry: () -> Unit) : LoadStateAdapter<BodyPartWorkoutFooterAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: LoadStateScreenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MyViewHolder, loadState: LoadState) {
        holder.binding.apply {
            retry.isVisible = loadState !is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error

            progressBar.isVisible = loadState !is LoadState.NotLoading
            progressBar.isVisible = loadState is LoadState.Loading

            errorText.isVisible = loadState !is LoadState.NotLoading
            errorText.isVisible = loadState is LoadState.Error

            retry.setOnClickListener {
                onRetry()
                retry.isEnabled = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MyViewHolder {
        val binding =
            LoadStateScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}