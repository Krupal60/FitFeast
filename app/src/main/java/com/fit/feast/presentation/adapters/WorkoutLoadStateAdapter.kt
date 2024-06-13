package com.fit.feast.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fit.feast.databinding.LoadStateScreenBinding

class WorkoutLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<WorkoutLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LoadStateScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: LoadStateScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.retry.setOnClickListener {
                retry.invoke()
            }
            binding.apply {

                progressBar.isVisible = loadState is LoadState.Loading
                progressBar.isVisible = loadState !is LoadState.Error

                retry.isVisible = loadState !is LoadState.Loading
                retry.isVisible = loadState is LoadState.Error

                errorText.isVisible = loadState !is LoadState.Loading
                errorText.isVisible = loadState is LoadState.Error
            }
        }
    }
}
