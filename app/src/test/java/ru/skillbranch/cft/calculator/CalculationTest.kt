package ru.skillbranch.cft.calculator

import org.junit.Test
import ru.skillbranch.cft.calculator.utils.CalculationUtils
import ru.skillbranch.cft.calculator.utils.ExpressionEvaluationUtils

class CalculationTest {

    @Test
    fun test_calculation() {
        val expression = "3 + 4*2.4"
        val expression2 = "-3 + 4*2.4 + (4.5*9) + 78/4"
        val expression3 = "10 + 2 * 6"
        val result = ExpressionEvaluationUtils.evaluateExpression(expression2)
//        val result = CalculationUtils.calculateExpression(expression)
        println(result)
    }
}