package ru.skillbranch.cft.calculator.models

import java.lang.Exception
import java.math.BigDecimal

enum class Operator(val precedence: Int) {

    ADD(0) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            return firstOperand + secondOperand
        }
    },
    SUBTRACT(2) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            return firstOperand - secondOperand
        }
    },
    MULTIPLY(3) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            return firstOperand * secondOperand
        }
    },
    DIVIDE(4) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            if (secondOperand == BigDecimal.ZERO) {
                throw Exception("Cannot divide by 0")
            }
            return firstOperand / secondOperand
        }
    };

    abstract fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal
}