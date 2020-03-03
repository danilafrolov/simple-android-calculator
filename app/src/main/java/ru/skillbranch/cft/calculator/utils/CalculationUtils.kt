package ru.skillbranch.cft.calculator.utils

import ru.skillbranch.cft.calculator.constants.*
import ru.skillbranch.cft.calculator.interfaces.BaseOperator
import ru.skillbranch.cft.calculator.utils.Operators.OPERATORS
import java.math.BigDecimal
import java.util.*


object CalculationUtils {

    /**
     * Если результат операции превышает заданное число, то записываем его в экспоненциальной форме, иначе оставляем как есть
     */
    private val maxPlainNumber = BigDecimal("999999999")

    /** Получение оператора по его символу
     * @param token - символ оператора
     */
    private fun getOperator(token: String): BaseOperator {
        if (OPERATORS.containsKey(token)) {
            return OPERATORS[token]!!
        }
        throw IllegalArgumentException("Unknown operator: $token")
    }

    /**
     * Преобразует выражение из инфиксной нотации в обратную польскую нотацию по алгоритму "Сортировочная
     * станция" Эдскера Дейкстры.
     * @param infixExpression выражение в инфиксной форме.
     */
    fun getPostfixExpression(infixExpression: String): String {
        // Удаление пробелов из исходного выражения
        val expression = infixExpression.replace(" ", "")
        val result = ArrayList<String>()
        val operationStack = Stack<String>()

        // Индекс, на котором закончился разбор строки на прошлой итерации
        var currentIndex = 0
        while (true) {
            // индекс следующего оператора в выражении
            var nextOperatorIndex = expression.indexOfAny(OPERATORS.keys, currentIndex)
            if (nextOperatorIndex == -1) {
                break
            }
            var token = expression[nextOperatorIndex].toString()
            // Если знак оператора в экспоненциальной форме числа (например, 1.5E+8) или оператор - унарный минус, то игнорируем его и ищем следующий оператор
            if ((nextOperatorIndex > 0 && expression[nextOperatorIndex - 1] == 'E') ||
                (expression[nextOperatorIndex].toString() == SUBTRACT && expression.getOrNull(nextOperatorIndex - 1)?.toString() == LEFT_PARENTHESIS)
            ) {
                nextOperatorIndex = expression.indexOfAny(OPERATORS.keys, nextOperatorIndex + 1)
                token = expression[nextOperatorIndex].toString()
                if (nextOperatorIndex == -1) {
                    break
                }
            }
            // Если оператору или скобке предшествует операнд, добавляем его в выходную строку
            if (currentIndex != nextOperatorIndex) {
                result.add(expression.substring(currentIndex, nextOperatorIndex))
            }
            when (token) {
                LEFT_PARENTHESIS -> operationStack.push(token)
                RIGHT_PARENTHESIS -> {
                    while (operationStack.peek() != LEFT_PARENTHESIS) {
                        result.add(operationStack.pop().toString())
                    }
                    operationStack.pop()
                }
                else -> {
                    // Оператор
                    while (!operationStack.isEmpty() && operationStack.peek() != LEFT_PARENTHESIS
                        && getOperator(token).precedence <= getOperator(operationStack.peek()).precedence
                    ) {
                        result.add(operationStack.pop().toString())
                    }
                    operationStack.push(token)
                }
            }
            currentIndex = nextOperatorIndex + 1
        }

        // Добавление в выходную строку операндов после последнего оператора
        if (currentIndex != expression.length) {
            result.add(expression.substring(currentIndex))
        }
        // Добавление к выходной строке операторов из стека операторов
        while (!operationStack.empty()) {
            result.add(operationStack.pop().toString())
        }
        return result.joinToString(" ")
    }

    /**
     * Вычисляет значение выражения, записанного в инфиксной нотации. Выражение может содержать скобки, числа с
     * плавающей точкой, четыре основных математических операнда.
     *
     * @param expression выражение.
     * @return результат вычисления.
     */
    fun calculateExpression(expression: String): BigDecimal {
        val postfixExpression = getPostfixExpression(expression)
        val tokens = postfixExpression.split("\\s".toRegex())
        val numbers = Stack<BigDecimal>()
        for (token in tokens) {
            // Операнд
            if (!OPERATORS.containsKey(token)) {
                val number: BigDecimal
                try {
                    number = BigDecimal(token)
                } catch (e: NumberFormatException) {
                    throw NumberFormatException("Incorrect input number: $token")
                }
                numbers.push(number)
            } else {
                // Оператор - выполняем вычисление для верхних двух элементов стека
                val secondOperand = numbers.pop()
                val firstOperand = if (numbers.empty()) BigDecimal.ZERO else numbers.pop()
                val result = getOperator(token).applyOperator(firstOperand, secondOperand)
                if (result > maxPlainNumber) {
                    numbers.push(result)
                } else {
                    numbers.push(BigDecimal(result.toPlainString()))
                }
            }
        }
        if (numbers.size != 1) {
            throw IllegalArgumentException("Expression syntax error.")
        }
        // Последний элемент стека - результат вычисления выражения
        return numbers.pop()
    }
}