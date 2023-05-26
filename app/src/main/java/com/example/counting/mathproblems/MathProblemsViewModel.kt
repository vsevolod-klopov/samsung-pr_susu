package com.example.counting.mathproblems

import com.example.counting.BaseViewModel

class MathProblemsViewModel : BaseViewModel(){

    override fun getNewTask() {
        super.getNewTask()
        generateProblem(level+1)
    }

    fun generateProblem(numbersCount: Int) {
        val number = generateNumber()
        numbers.add(number)
        operationString = number.toString()
        repeat(numbersCount - 1) { operationString = addOperation(numbers.last(), operationString) }
        expressionResult = numbers.sum()
        numbers.clear()
    }
}