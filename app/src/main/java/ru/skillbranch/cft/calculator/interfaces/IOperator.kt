package ru.skillbranch.cft.calculator.interfaces

import java.math.BigDecimal

interface IOperator {
    val precedence: Int

    fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal
}