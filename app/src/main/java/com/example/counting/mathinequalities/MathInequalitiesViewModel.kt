package com.example.counting.mathinequalities

import com.example.counting.BaseViewModel

class MathInequalitiesViewModel : BaseViewModel() {

    private var rightOperationString = ""
    var rightExpressionResult = 0
    var rightLevel = 0

    var expressionString = ""

    override var timeDifficulty = 30

    override fun getNewTask() {
        super.getNewTask()
        if (level % 3 == 0) rightLevel++
        if (rightLevel == 3) rightLevel = 0
        generateInequality(level+1, rightLevel)
    }

    override fun reset() {
        super.reset()
        rightExpressionResult = 0
        rightOperationString = ""
    }

    fun generateInequality(numbersCount1: Int, numbersCount2: Int){
        val lSide = getSide(numbersCount1)
        operationString = lSide.first
        expressionResult = lSide.second

        val rSide = getSide(numbersCount2)
        rightOperationString = rSide.first
        rightExpressionResult = rSide.second


        expressionString = "$operationString ? $rightOperationString"
    }

    private fun getSide(numbersCount: Int): Pair<String, Int> {
        val number = generateNumber()
        numbers.add(number)
        var opString = number.toString()
        repeat(numbersCount - 1) { opString = addOperation(numbers.last(), opString) }
        var expResult = numbers.sum()
        numbers.clear()

        return Pair(opString, expResult)
    }

}