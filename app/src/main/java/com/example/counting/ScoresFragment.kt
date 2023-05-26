package com.example.counting

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.counting.databinding.FragmentScoresBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class ScoresFragment : Fragment(R.layout.fragment_scores) {

    private lateinit var binding: FragmentScoresBinding
    private lateinit var adapter: ScoresAdapter
    private val scores = ArrayList<Pair<String, Int>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentScoresBinding.bind(requireView())

        initViews()

    }

    private fun initViews(){

        val args: ScoresFragmentArgs by navArgs()

        when (args.mode) {
            "0" -> binding.yourScore.isVisible = false
            "1" -> binding.yourScore.text = resources.getString(R.string.score, args.score)
        }

        adapter = ScoresAdapter(scores.asReversed())

        val scoresRV = binding.scoresRV
        scoresRV.layoutManager = LinearLayoutManager(context)
        scoresRV.adapter = adapter

        binding.btnClear.setOnClickListener {
            val file = File(requireContext().filesDir, "scores.txt")
            file.writeText("")
            scores.clear()
            adapter.notifyDataSetChanged()
        }

        readFromFile()
    }

    private fun readFromFile() {
        val file = File(requireContext().filesDir, "scores.txt")
        try {
            BufferedReader(FileReader(file)).use { br ->
                br.lines().forEach {
                    scores.add(Pair(it.split("|").first(), it.split("|").last().toInt()))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        adapter.notifyDataSetChanged()
    }
}