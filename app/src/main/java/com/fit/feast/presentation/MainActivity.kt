package com.fit.feast.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.fragment.NavHostFragment
import com.fit.feast.R
import com.fit.feast.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navFragment.navController
        binding.apply {

            bottomNav.selectedItemId = R.id.workoutFragment
            bottomNav.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.workoutFragment -> {
                        if (navController.currentDestination?.id != R.id.workoutFragment) {
                            navController.navigate(R.id.action_recipesFragment_to_homeScreen)
                        }
                        true
                    }

                    R.id.recipesFragment -> {
                        navController.navigate(R.id.action_homeScreen_to_recipesFragment)

                        true
                    }

                    else -> {
                        false
                    }
                }
            }
            navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
                val isAtHome = destination.hierarchy.any { it.id == R.id.workoutFragment }
                if (isAtHome) {
                    binding.bottomNav.selectedItemId = R.id.workoutFragment
                }
                if( destination.hierarchy.any { it.id != R.id.workoutFragment } || destination.hierarchy.any { it.id != R.id.recipesFragment }){
                    binding.bottomNav.visibility = View.GONE
                }
                if( destination.hierarchy.any { it.id == R.id.workoutFragment } || destination.hierarchy.any { it.id == R.id.recipesFragment }){
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}