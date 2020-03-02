package ru.skillbranch.cft.calculator.utils

import ru.skillbranch.cft.calculator.constants.*
import ru.skillbranch.cft.calculator.interfaces.BaseOperator
import ru.skillbranch.cft.calculator.utils.Operators.OPERATORS
import java.math.BigDecimal
import java.util.*


object CalculationUtils {

    private fun getOperator(token: String): BaseOperator {
        if (OPERATORS.containsKey(token)) {
            return OPERATORS[token]!!
        }
        throw IllegalArgumentException("Unknown operator: $token")
    }

    fun getPostfixExpression(infixExpression: String): String {
        val expression = infixExpression.replace(" ", "")
        val result = ArrayList<String>()
        val operationStack = Stack<String>()

        var currentIndex = 0
        var findNext = true
        while (findNext) {
            var nextOperatorIndex = expression.indexOfAny(OPERATORS.keys, currentIndex)
            if (nextOperatorIndex == -1) {
                findNext = false
                continue
            }
            var token = expression[nextOperatorIndex].toString()
            if ((nextOperatorIndex > 0 && expression[nextOperatorIndex - 1] == 'E') ||
                (expression[nextOperatorIndex].toString() == SUBTRACT && expression.getOrNull(nextOperatorIndex - 1)?.toString() == LEFT_PARENTHESIS)
            ) {
                nextOperatorIndex = expression.indexOfAny(OPERATORS.keys, nextOperatorIndex + 1)
                token = expression[nextOperatorIndex].toString()
            }
            if (currentIndex != nextOperatorIndex) {
                result.add(expression.substring(currentIndex, nextOperatorIndex))
            }
            when (token) {
                LEFT_PARENTHESIS -> operationStack.push(token)
                RIGHT_PARENTHESIS -> {
                    while (operationStack.peek() != LEFT_PARENTHESIS) {
                        result.add(operationStack.pop().toString())
                    }
                    operationStack.pop()
                }
                else -> {
                    while (!operationStack.isEmpty() && operationStack.peek() != LEFT_PARENTHESIS
                        && getOperator(token).precedence <= getOperator(operationStack.peek()).precedence
                    ) {
                        result.add(operationStack.pop().toString())
                    }
                    operationStack.push(token)
                }
            }
            currentIndex = nextOperatorIndex + 1
        }

        if (currentIndex != expression.length) {
            result.add(expression.substring(currentIndex))
        }
        while (!operationStack.empty()) {
            result.add(operationStack.pop().toString())
        }
        return result.joinToString(" ")
    }

    fun calculateExpression(expression: String): BigDecimal {
        val postfixExpression = getPostfixExpression(expression)
        val tokens = postfixExpression.split("\\s".toRegex())
        val stack = Stack<BigDecimal>()
        for (token in tokens) {
            // Операнд.
            if (!OPERATORS.containsKey(token)) {
                val number: BigDecimal
                try {
                    number = BigDecimal(token)
                } catch (e: NumberFormatException) {
                    throw NumberFormatException("Incorrect input number: $token")
                }
                stack.push(number)
            } else {
                val secondOperand = stack.pop()
                val firstOperand = if (stack.empty()) BigDecimal.ZERO else stack.pop()
                val result = getOperator(token).applyOperator(firstOperand, secondOperand)
                if (result > BigDecimal("99999999")) {
                    stack.push(result)
                } else {
                    stack.push(BigDecimal(result.toPlainString()))
                }
            }
        }
        if (stack.size != 1)
            throw IllegalArgumentException("Expression syntax error.")
        return stack.pop()
    }
}