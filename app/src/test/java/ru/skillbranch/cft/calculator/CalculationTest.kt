package ru.skillbranch.cft.calculator

import org.junit.Test
import ru.skillbranch.cft.calculator.utils.CalculationUtils

class CalculationTest {

    @Test
    fun test_calculation() {
        val expression1 = "3 + 4*2.4"
        val expression2 = "(3 + 5)*6+(9-2)*5"
        val expression3 = "(3+5)*6+(9-2)*5"
        val expression4 = "10 + 2 * 6"
        println(CalculationUtils.getPostfixExpression(expression1))
        println(CalculationUtils.calculateExpression(expression1))
        println(CalculationUtils.calculateExpression(expression2))
        println(CalculationUtils.calculateExpression(expression3))
        println(CalculationUtils.calculateExpression(expression4))
    }
}