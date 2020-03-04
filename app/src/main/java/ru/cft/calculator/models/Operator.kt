package ru.cft.calculator.models

import ru.cft.calculator.interfaces.BaseOperator
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.IllegalArgumentException

/**
 * Реализация операторов для калькулятора, содержащая четыре основных математических оператора и скобки
 */
enum class Operator(override val precedence: Int) : BaseOperator {

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
            if (secondOperand.compareTo(BigDecimal.ZERO) == 0) {
                throw IllegalArgumentException("Division by zero")
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