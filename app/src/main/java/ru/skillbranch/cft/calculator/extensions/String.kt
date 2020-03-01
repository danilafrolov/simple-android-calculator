package ru.skillbranch.cft.calculator.extensions

import ru.skillbranch.cft.calculator.constants.*

private val operators = listOf(ADD, SUBTRACT, MULTIPLY, DIVIDE, POINT)

fun String.hasOperators(): Boolean {
    return this.any { operators.minus(POINT).contains(it.toString()) }
}

fun String.isValidExpression(): Boolean {
    // первый символ в выражении - оператор (кроме минуса)
    if (operators.minus(SUBTRACT).contains(this.firstOrNull()?.toString())) {
        return false
    }
    // последний символ в выражении - оператор
    if (operators.contains(this.lastOrNull()?.toString())) {
        return false
    }

    // количество открывающих скобок не соответствует количеству закрывающих
    if (this.hasUnmatchedParentheses()) {
        return false
    }

    // два оператора подряд
    if (this.hasTwoOperatorsInARow()) {
        return false
    }
    return true
}

private fun String.hasUnmatchedParentheses(): Boolean {
    return this.count { it.toString() == LEFT_PARENTHESIS } != this.count { it.toString() == RIGHT_PARENTHESIS }
}

private fun String.hasTwoOperatorsInARow(): Boolean {
    var currentOperatorIndex = this.indexOfAny(operators)
    while (currentOperatorIndex >= 0) {
        if (operators.contains(this.getOrNull(currentOperatorIndex + 1)?.toString())) {
            return true
        }
        currentOperatorIndex = this.indexOfAny(operators, currentOperatorIndex + 1)
    }
    return false
}