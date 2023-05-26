package com.example.counting.mathinequalities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.counting.R
import com.example.counting.databinding.FragmentMathInequalitiesBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

class MathInequalitiesFragment : Fragment(R.layout.fragment_math_inequalities) {

    private lateinit var binding: FragmentMathInequalitiesBinding
    private var timer: CountDownTimer? = null
    private val viewModel: MathInequalitiesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMathInequalitiesBinding.bind(requireView())

        initViews()
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    private fun initViews() {
        if (viewModel.operationString == "") {
            viewModel.generateInequality(viewModel.level + 1, viewModel.rightLevel)
            binding.problemTV.text = viewModel.expressionString
            startTimer((viewModel.timeDifficulty * 1000).toLong())
        }

        with(binding) {

            livesLeft.text = resources.getString(R.string.livesLeft, viewModel.lives)

            score.text = resources.getString(R.string.score, viewModel.score)

            greaterButton.text = "≥"

            lessButton.text = "<"

            greaterButton.setOnClickListener {
                if (viewModel.expressionResult >= viewModel.rightExpressionResult) {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.right_answer),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.score++
                } else {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.wrong_answer),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.lives--
                }
                updateInequality()
                if (viewModel.lives == 0) finishGame()
            }

            lessButton.setOnClickListener {
                if (viewModel.expressionResult < viewModel.rightExpressionResult) {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.right_answer),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.score++
                } else {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.wrong_answer),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.lives--
                }
                updateInequality()
                if (viewModel.lives == 0) finishGame()
            }

            refreshButton.setOnClickListener {
                it.isVisible = false
                updateInequality()
            }
        }
    }

    private fun startTimer(timeMillis: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                binding.timerText.text = "Осталось " + (p0 / 1000).toString() + " секунд"
            }

            override fun onFinish() {
                Snackbar.make(
                    requireView(), resources.getString(R.string.times_up), Snackbar.LENGTH_LONG
                ).show()
                timer?.cancel()
                viewModel.lives--
                updateInequality()
                if (viewModel.lives == 0) finishGame()
            }
        }.start()
    }

    private fun updateInequality() {
        viewModel.getNewTask()
        with(binding) {
            problemTV.text = viewModel.expressionString
            livesLeft.text = resources.getString(R.string.livesLeft, viewModel.lives)
            score.text = resources.getString(R.string.score, viewModel.score)
        }
        startTimer((viewModel.timeDifficulty * 1000).toLong())
    }

    private fun finishGame() {
        writeToFile()
        viewModel.reset()
        val action =
            MathInequalitiesFragmentDirections.actionMathInequalitiesFragmentToScoresFragment(
                "1",
                viewModel.score
            )
        findNavController().navigate(action)
        Snackbar.make(
            requireView(), resources.getString(R.string.gamesOver), Snackbar.LENGTH_LONG
        ).show()
    }

    private fun writeToFile(){
        val file = File(requireContext().filesDir, "scores.txt")
        file.appendText(
            resources.getString(R.string.solving_inequalities) + "|" + viewModel.score.toString() + "\n"
        )
    }
}