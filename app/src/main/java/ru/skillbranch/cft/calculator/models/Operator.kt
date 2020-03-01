package ru.skillbranch.cft.calculator.models

import ru.skillbranch.cft.calculator.interfaces.IOperator
import java.lang.Exception
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

enum class Operator(override val precedence: Int) : IOperator {

    ADD(1) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            return firstOperand.add(secondOperand, MathContext(8, RoundingMode.HALF_UP)).stripTrailingZeros()
        }
    },
    SUBTRACT(2) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            return firstOperand.subtract(secondOperand, MathContext(8, RoundingMode.HALF_UP)).stripTrailingZeros()
        }
    },
    MULTIPLY(3) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            return firstOperand.multiply(secondOperand, MathContext(8, RoundingMode.HALF_UP)).stripTrailingZeros()
        }
    },
    DIVIDE(4) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            if (secondOperand == BigDecimal.ZERO) {
                throw Exception("Cannot divide by 0")
            }
            return firstOperand.divide(secondOperand, MathContext(8, RoundingMode.HALF_UP)).stripTrailingZeros()
        }
    },
    PARENTHESIS(5) {
        override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal {
            return BigDecimal.ZERO
        }
    };

    abstract override fun applyOperator(firstOperand: BigDecimal, secondOperand: BigDecimal): BigDecimal
}