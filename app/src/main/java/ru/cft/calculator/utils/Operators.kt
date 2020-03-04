package ru.cft.calculator.utils

import ru.cft.calculator.constants.*
import ru.cft.calculator.interfaces.BaseOperator
import ru.cft.calculator.models.Operator

object Operators {

    val OPERATORS = hashMapOf<String, BaseOperator>(
        MULTIPLY to Operator.MULTIPLY,
        DIVIDE to Operator.DIVIDE,
        ADD to Operator.ADD,
        SUBTRACT to Operator.SUBTRACT,
        LEFT_PARENTHESIS to Operator.PARENTHESIS,
        RIGHT_PARENTHESIS to Operator.PARENTHESIS)
}