package com.example.counting.mathproblems

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
import com.example.counting.databinding.FragmentMathProblemsBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

class MathProblemsFragment : Fragment(R.layout.fragment_math_problems) {

    private lateinit var binding: FragmentMathProblemsBinding
    private var timer: CountDownTimer? = null
    private val viewModel: MathProblemsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMathProblemsBinding.bind(requireView())
        initViews()

    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    private fun initViews() {

        if (viewModel.operationString == "") {
            viewModel.generateProblem(viewModel.level + 1)
            binding.problemTV.text = viewModel.operationString
            startTimer((viewModel.timeDifficulty * 1000).toLong())
        }

        with(binding) {
            livesLeft.text = resources.getString(R.string.livesLeft, viewModel.lives)

            score.text = resources.getString(R.string.score, viewModel.score)

            doneButton.setOnClickListener {

                val answer = try {
                    binding.answerField.text.toString().toInt()
                } catch (e: Exception) {
                    0
                }
                if (answer == viewModel.expressionResult) {
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
                updateProblem()
                if (viewModel.lives == 0) finishGame()
            }

            refreshButton.setOnClickListener {
                it.isVisible = false
                updateProblem()
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
                updateProblem()
                if (viewModel.lives == 0) finishGame()
            }
        }.start()
    }

    private fun updateProblem() {
        viewModel.getNewTask()
        with(binding) {
            problemTV.text = viewModel.operationString
            answerField.setText("")
            livesLeft.text = resources.getString(R.string.livesLeft, viewModel.lives)
            score.text = resources.getString(R.string.score, viewModel.score)
        }
        startTimer((viewModel.timeDifficulty * 1000).toLong())
    }

    private fun finishGame() {
        writeToFile()
        viewModel.reset()
        val action =
            MathProblemsFragmentDirections.actionMathProblemsFragmentToScoresFragment(
                "1",
                viewModel.score
            )
        findNavController().navigate(action)
        Snackbar.make(
            requireView(), resources.getString(R.string.gamesOver), Snackbar.LENGTH_LONG
        ).show()
    }

    private fun writeToFile() {
        val file = File(requireContext().filesDir, "scores.txt")
        file.appendText(
            resources.getString(R.string.solving_problems) + "|" + viewModel.score.toString() + "\n"
        )
    }

}