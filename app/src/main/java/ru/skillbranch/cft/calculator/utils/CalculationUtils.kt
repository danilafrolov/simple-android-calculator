package ru.skillbranch.cft.calculator.utils

import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.absoluteValue


object CalculationUtils {

    private val MAIN_MATH_OPERATIONS = hashMapOf(
        "×" to 1,
        "÷" to 1,
        "+" to 2,
        "-" to 2
    )

    private fun sortingStation(
        expression: String?, operations: Map<String, Int>, leftBracket: String,
        rightBracket: String
    ): String {
        var expression = expression
        if (expression == null || expression.isEmpty())
            throw IllegalStateException("Expression isn't specified.")
        if (operations == null || operations.isEmpty())
            throw IllegalStateException("Operations aren't specified.")
        // Выходная строка, разбитая на "символы" - операции и операнды..
        val out = ArrayList<String>()
        // Стек операций.
        val stack = Stack<String>()

        // Удаление пробелов из выражения.
        expression = expression.replace(" ", "")

        // Множество "символов", не являющихся операндами (операции и скобки).
        val operationSymbols = HashSet(operations.keys)
        operationSymbols.add(leftBracket)
        operationSymbols.add(rightBracket)

        // Индекс, на котором закончился разбор строки на прошлой итерации.
        var index = 0
        // Признак необходимости поиска следующего элемента.
        var findNext = true
        while (findNext) {
            var nextOperationIndex = expression.length
            var nextOperation = ""
            // Поиск следующего оператора или скобки.
            for (operation in operationSymbols) {
                val i = expression.indexOf(operation, index)
                if (i in 0 until nextOperationIndex) {
                    nextOperation = operation
                    nextOperationIndex = i
                }
            }
            // Оператор не найден.
            if (nextOperationIndex == expression.length) {
                findNext = false
            } else {
                // Если оператору или скобке предшествует операнд, добавляем его в выходную строку.
                if (index != nextOperationIndex) {
                    out.add(expression.substring(index, nextOperationIndex))
                }
                // Обработка операторов и скобок.
                // Открывающая скобка.
                when (nextOperation) {
                    leftBracket -> stack.push(nextOperation)
                    rightBracket -> {
                        while (stack.peek() != leftBracket) {
                            out.add(stack.pop())
                            if (stack.empty()) {
                                throw IllegalArgumentException("Unmatched brackets")
                            }
                        }
                        stack.pop()
                    }
                    else -> {
                        while (!stack.empty() && stack.peek() != leftBracket &&
                            operations[nextOperation]!! >= operations[stack.peek()]!!
                        ) {
                            out.add(stack.pop())
                        }
                        stack.push(nextOperation)
                    }
                }// Операция.
                // Закрывающая скобка.
                index = nextOperationIndex + nextOperation.length
            }
        }
        // Добавление в выходную строку операндов после последнего операнда.
        if (index != expression.length) {
            out.add(expression.substring(index))
        }
        // Пробразование выходного списка к выходной строке.
        while (!stack.empty()) {
            out.add(stack.pop())
        }
        val result = StringBuffer()
        if (out.isNotEmpty())
            result.append(out.removeAt(0))
        while (out.isNotEmpty())
            result.append(" ").append(out.removeAt(0))

        return result.toString()
    }

    fun sortingStation(expression: String, operations: Map<String, Int>): String {
        return sortingStation(expression, operations, "(", ")")
    }

    fun calculateExpression(expression: String): BigDecimal {
        val rpn = sortingStation(expression, MAIN_MATH_OPERATIONS)
        val tokenizer = StringTokenizer(rpn, " ")
        val stack = Stack<BigDecimal>()
        while (tokenizer.hasMoreTokens()) {
            val token = tokenizer.nextToken()
            // Операнд.
            if (!MAIN_MATH_OPERATIONS.containsKey(token)) {
                stack.push(BigDecimal(token))
            } else {
                val operand2 = stack.pop()
                val operand1 = if (stack.empty()) BigDecimal.ZERO else stack.pop()
                when (token) {
                    "×" -> stack.push(operand1.multiply(operand2))
                    "÷" -> stack.push(operand1.divide(operand2))
                    "+" -> stack.push(operand1.add(operand2))
                    "-" -> stack.push(operand1.subtract(operand2))
                }
            }
        }
        if (stack.size !== 1)
            throw IllegalArgumentException("Expression syntax error.")
        return stack.pop()
    }
}