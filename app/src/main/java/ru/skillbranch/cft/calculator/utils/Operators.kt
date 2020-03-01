package ru.skillbranch.cft.calculator.utils

import ru.skillbranch.cft.calculator.constants.LEFT_PARENTHESIS
import ru.skillbranch.cft.calculator.constants.RIGHT_PARENTHESIS
import ru.skillbranch.cft.calculator.interfaces.BaseOperator
import ru.skillbranch.cft.calculator.models.Operator

object Operators {

    val OPERATORS = hashMapOf<String, BaseOperator>(
        "×" to Operator.MULTIPLY,
        "÷" to Operator.DIVIDE,
        "+" to Operator.ADD,
        "-" to Operator.SUBTRACT,
        LEFT_PARENTHESIS to Operator.PARENTHESIS,
        RIGHT_PARENTHESIS to Operator.PARENTHESIS)
}