package com.fit.feast.presentation.screens

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.feast.R
import com.fit.feast.presentation.viewmodel.ByEquipmentsViewModel

class ByEquipmentsFragment : Fragment() {

    companion object {
        fun newInstance() = ByEquipmentsFragment()
    }

    private val viewModel: ByEquipmentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_by_equipments, container, false)
    }
}