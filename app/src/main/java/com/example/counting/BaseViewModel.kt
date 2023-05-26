package com.example.counting

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    var score = 0

    var level = 1
    var difficulty = 0
    var operationString = ""
    var expressionResult = 0
    var lives = 3
    open var timeDifficulty = 25
    var numbers = ArrayList<Int>()

    open fun addOperation(prevNumber: Int, opString: String) : String {
        val number = generateNumber()
        val operation: String
        when ((0..2).random()) {
            0 -> {
                operation = " + "
                numbers.add(number)
            }
            1 -> {
                operation = " - "
                numbers.add(-1 * number)
            }
            2 -> {
                operation = " * "
                val innerNumber = number * prevNumber
                numbers.removeLast()
                numbers.add(innerNumber)
            }
            else -> operation = "Something gone wrong"
        }
        return opString + operation + number.toString()
    }

    fun generateNumber(): Int {
        val number = when (difficulty) {
            0 -> (1..10).random()
            1 -> (1..15).random()
            2 -> (7..20).random()
            3 -> (-10..20).random()
            else -> 0
        }
        return number
    }

    open fun getNewTask() {
        level++
        if (level % 4 == 0) {
            difficulty++
            level = 1
            if (difficulty == 4) {
                difficulty = 0
                timeDifficulty--
            }
        }
    }

    open fun reset() {
        score = 0
        level = 1
        difficulty = 0
        operationString = ""
        expressionResult = 0
        lives = 3
        timeDifficulty = 25
        numbers.clear()
    }

}