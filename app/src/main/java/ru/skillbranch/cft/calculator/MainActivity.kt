package ru.skillbranch.cft.calculator

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.cft.calculator.constants.*
import ru.skillbranch.cft.calculator.extensions.*
import ru.skillbranch.cft.calculator.utils.CalculationUtils
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val operators = listOf(ADD, SUBTRACT, MULTIPLY, DIVIDE, LEFT_PARENTHESIS, RIGHT_PARENTHESIS, POINT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            tv_expression.showSoftInputOnFocus = false
        } else { // API 11-20
            tv_expression.setTextIsSelectable(true)
        }
        initViews()
    }

    private fun initViews() {
        initActionButtons()
        initDigitButtons()
    }

    private fun initDigitButtons() {
        btn_digit_zero.setOnClickListener {
            onDigitButtonClick(ZERO)
        }
        btn_digit_one.setOnClickListener {
            onDigitButtonClick(ONE)
        }
        btn_digit_two.setOnClickListener {
            onDigitButtonClick(TWO)
        }
        btn_digit_three.setOnClickListener {
            onDigitButtonClick(THREE)
        }
        btn_digit_four.setOnClickListener {
            onDigitButtonClick(FOUR)
        }
        btn_digit_five.setOnClickListener {
            onDigitButtonClick(FIVE)
        }
        btn_digit_six.setOnClickListener {
            onDigitButtonClick(SIX)
        }
        btn_digit_seven.setOnClickListener {
            onDigitButtonClick(SEVEN)
        }
        btn_digit_eight.setOnClickListener {
            onDigitButtonClick(EIGHT)
        }
        btn_digit_nine.setOnClickListener {
            onDigitButtonClick(NINE)
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
            onDigitButtonClick(LEFT_PARENTHESIS)
        }
        btn_right_parenthesis.setOnClickListener {
            onDigitButtonClick(RIGHT_PARENTHESIS)
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

    private fun onDeleteButtonClick() {
        tv_expression.text.clear()
        tv_result.text = ""
    }

    private fun onResultButtonClick() {
        val expression = tv_expression.text?.toString()
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
            tv_expression.setText(result.toString())
            tv_expression.setSelection(tv_expression.text.length)
            tv_result.text = ""
        } catch (e: Exception) {
            tv_result.text = e.message
            println(e)
        }
    }

    private fun onDigitButtonClick(number: String) {
        val start = tv_expression.selectionStart
        val end = tv_expression.selectionEnd
        tv_expression.text.replace(start, end, number)
        evaluateExpression()
    }

    private fun onOperatorButtonClick(operator: String) {
        val start = tv_expression.selectionStart
        val end = tv_expression.selectionEnd
        val text = tv_expression.text.toString()
        if (operators.contains(text.getOrNull(start - 1)?.toString()) || operators.contains(text.getOrNull(end)?.toString())) {
            return
        }
        tv_expression.text.replace(start, end, operator)
        evaluateExpression()
    }

    private fun onBackspaceButtonClick() {
        val start = tv_expression.selectionStart
        val end = tv_expression.selectionEnd
        if (start == end) {
            val length = tv_expression.text.length
            if (length > 0 && start > 0) {
                tv_expression.text.delete(start - 1, start)
            }
        } else {
            tv_expression.text.replace(start, end, "")
        }
        evaluateExpression()
    }

    private fun evaluateExpression() {
        val expression = tv_expression.text?.toString()
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