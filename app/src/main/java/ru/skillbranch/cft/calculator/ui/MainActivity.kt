package ru.skillbranch.cft.calculator.ui

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.cft.calculator.R
import ru.skillbranch.cft.calculator.constants.*
import ru.skillbranch.cft.calculator.extensions.*
import ru.skillbranch.cft.calculator.utils.CalculationUtils
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val operators = listOf(ADD, SUBTRACT, MULTIPLY, DIVIDE, POINT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        preventKeyboardAppearing()
    }

    private fun initViews() {
        initActionButtons()
        initDigitButtons()
    }

    private fun initDigitButtons() {
        btn_digit_zero.setOnClickListener {
            onDigitOrParenthesesButtonClick(ZERO)
        }
        btn_digit_one.setOnClickListener {
            onDigitOrParenthesesButtonClick(ONE)
        }
        btn_digit_two.setOnClickListener {
            onDigitOrParenthesesButtonClick(TWO)
        }
        btn_digit_three.setOnClickListener {
            onDigitOrParenthesesButtonClick(THREE)
        }
        btn_digit_four.setOnClickListener {
            onDigitOrParenthesesButtonClick(FOUR)
        }
        btn_digit_five.setOnClickListener {
            onDigitOrParenthesesButtonClick(FIVE)
        }
        btn_digit_six.setOnClickListener {
            onDigitOrParenthesesButtonClick(SIX)
        }
        btn_digit_seven.setOnClickListener {
            onDigitOrParenthesesButtonClick(SEVEN)
        }
        btn_digit_eight.setOnClickListener {
            onDigitOrParenthesesButtonClick(EIGHT)
        }
        btn_digit_nine.setOnClickListener {
            onDigitOrParenthesesButtonClick(NINE)
        }
    }

    private fun initActionButtons() {
        btn_action_add.setOnClickListener {
            onOperatorButtonClick(ADD)
        }
        btn_action_subtract.setOnClickListener {
            onOperatorButtonClick(SUBTRACT)
        }
        btn_action_multiply.setOnClickListener {
            onOperatorButtonClick(MULTIPLY)
        }
        btn_action_divide.setOnClickListener {
            onOperatorButtonClick(DIVIDE)
        }
        btn_left_parenthesis.setOnClickListener {
            onDigitOrParenthesesButtonClick(LEFT_PARENTHESIS)
        }
        btn_right_parenthesis.setOnClickListener {
            onDigitOrParenthesesButtonClick(RIGHT_PARENTHESIS)
        }
        btn_dot.setOnClickListener {
            onOperatorButtonClick(POINT)
        }
        btn_backspace.setOnClickListener {
            onBackspaceButtonClick()
        }
        btn_action_result.setOnClickListener {
            onResultButtonClick()
        }
        btn_action_del.setOnClickListener {
            onDeleteButtonClick()
        }
    }

    private fun preventKeyboardAppearing() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            et_expression.showSoftInputOnFocus = false
        } else { // API 11-20
            et_expression.setTextIsSelectable(true)
        }
    }

    private fun onDeleteButtonClick() {
        et_expression.text.clear()
        tv_result.text = ""
    }

    private fun onResultButtonClick() {
        val expression = et_expression.text?.toString()
        if (expression.isNullOrEmpty()) {
            return
        }
        if (!expression.isValidExpression()) {
            val toast = Toast.makeText(applicationContext, INCORRECT_EXCEPTION_MESSAGE, LENGTH_SHORT)
            toast.show()
            return
        }
        try {
            val result = CalculationUtils.calculateExpression(expression.toString())
            et_expression.setText(result.toString())
            et_expression.setSelection(et_expression.text.length)
            tv_result.text = ""
        } catch (e: Exception) {
            tv_result.text = e.message
            println(e)
        }
    }

    private fun onDigitOrParenthesesButtonClick(number: String) {
        val start = et_expression.selectionStart
        val end = et_expression.selectionEnd
        et_expression.text.replace(start, end, number)
        evaluateExpression()
    }

    private fun onOperatorButtonClick(operator: String) {
        val start = et_expression.selectionStart
        val end = et_expression.selectionEnd
        val text = et_expression.text.toString()
        //  если на месте курсора уже есть оператор, то не позволяем добавить еще один
        if (operators.contains(text.getOrNull(start - 1)?.toString()) || operators.contains(text.getOrNull(end)?.toString())) {
            return
        }
        et_expression.text.replace(start, end, operator)
        evaluateExpression()
    }

    private fun onBackspaceButtonClick() {
        val start = et_expression.selectionStart
        val end = et_expression.selectionEnd
        if (start == end) {
            val length = et_expression.text.length
            if (length > 0 && start > 0) {
                et_expression.text.delete(start - 1, start)
            }
        } else {
            et_expression.text.replace(start, end, "")
        }
        evaluateExpression()
    }

    private fun evaluateExpression() {
        val expression = et_expression.text?.toString()
        if (expression.isNullOrEmpty() || !expression.hasOperators() || !expression.isValidExpression()) {
            tv_result.text = ""
            return
        }
        try {
            val result = CalculationUtils.calculateExpression(expression.toString())
            tv_result.text = result.toString()
        } catch (e: Exception) {
            println(e)
        }
    }
}