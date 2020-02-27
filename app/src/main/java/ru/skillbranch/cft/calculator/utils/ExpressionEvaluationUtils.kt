package ru.skillbranch.cft.calculator.utils

import ru.skillbranch.cft.calculator.models.Operator
import java.lang.StringBuilder
import java.math.BigDecimal
import java.util.*

object ExpressionEvaluationUtils {

    private val MAIN_MATH_OPERATIONS = hashMapOf(
        "*" to Operator.MULTIPLY,
        "/" to Operator.DIVIDE,
        "+" to Operator.ADD,
        "-" to Operator.SUBTRACT
    )

    fun evaluateExpression(expression: String): BigDecimal {
        val numbers = Stack<BigDecimal>()
        val operators = Stack<Char>()
        val tokens = expression.replace(" ", "").toCharArray()
        var i = 0
        while (i < tokens.count()) {
            when {
                tokens[i].isDigit() -> {
                    val num = StringBuilder()
                    while (i < tokens.count() && (tokens[i].isDigit() || tokens[i] == '.')) {
                        num.append(tokens[i])
                        i++
                    }
                    numbers.push(num.toString().toBigDecimal())
                }
                tokens[i] == '(' -> {
                    operators.push(tokens[i])
                    i++
                }
                tokens[i] == ')' -> {
                    while (operators.peek() != '(') {

                        numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()))
                    }
                    operators.pop()
                    i++
                }
                (tokens[i] == '+' || tokens[i] == '-' ||
                        tokens[i] == '*' || tokens[i] == '/') -> {

                    while (!operators.empty() && hasPrecedence(tokens[i], operators.peek())) {
                        numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()))
                    }
                    // Push current token to 'ops'.
                    operators.push(tokens[i])
                    i++
                }
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!operators.empty()) {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()))
        }
        return numbers.pop()
    }

    // Returns true if 'secondOperator' has higher or same precedence as 'firstOperator',
    // otherwise returns false.
    private fun hasPrecedence(firstOperator: Char, secondOperator: Char): Boolean {
        if (secondOperator == '(' || secondOperator == ')') {
            return false
        }
        return !((firstOperator == '*' || firstOperator == '/') && (secondOperator == '+' || secondOperator == '-'))
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    private fun applyOperator(op: Char, b: BigDecimal, a: BigDecimal): BigDecimal {
        when (op) {
            '+' -> return a + b
            '-' -> return a - b
            '*' -> return a * b
            '/' -> {
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    throw UnsupportedOperationException("Cannot divide by zero")
                }
                return a / b
            }
        }
        return BigDecimal.ZERO
    }
}