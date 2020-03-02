package ru.skillbranch.cft.calculator.utils

import ru.skillbranch.cft.calculator.constants.*
import ru.skillbranch.cft.calculator.interfaces.BaseOperator
import ru.skillbranch.cft.calculator.models.Operator

object Operators {

    val OPERATORS = hashMapOf<String, BaseOperator>(
        MULTIPLY to Operator.MULTIPLY,
        DIVIDE to Operator.DIVIDE,
        ADD to Operator.ADD,
        SUBTRACT to Operator.SUBTRACT,
        LEFT_PARENTHESIS to Operator.PARENTHESIS,
        RIGHT_PARENTHESIS to Operator.PARENTHESIS)
}