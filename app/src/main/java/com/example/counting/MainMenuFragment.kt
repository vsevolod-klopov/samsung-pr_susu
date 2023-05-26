package com.example.counting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.counting.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainMenuBinding.bind(requireView())

        with(binding) {
            btnProb.setOnClickListener { findNavController().navigate(R.id.action_mainMenuFragment_to_mathProblemsFragment) }
            btnIneq.setOnClickListener { findNavController().navigate(R.id.action_mainMenuFragment_to_mathInequalitiesFragment) }
            btnScoreTable.setOnClickListener {
                val action = MainMenuFragmentDirections.actionMainMenuFragmentToScoresFragment("0")
                findNavController().navigate(action)
            }
        }
    }
}