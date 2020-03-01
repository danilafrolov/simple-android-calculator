package ru.skillbranch.cft.calculator.interfaces

import java.math.BigDecimal

interface BaseOperator {
    val precedence: Int

    fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal
}