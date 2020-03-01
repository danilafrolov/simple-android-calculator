package ru.skillbranch.cft.calculator

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.cft.calculator.utils.CalculationUtils
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val ZERO: String = "0" // Value cannot be changed.
    private val ONE: String = "1"
    private val TWO: String = "2"
    private val THREE: String = "3"
    private val FOUR: String = "4"
    private val FIVE: String = "5"
    private val SIX: String = "6"
    private val SEVEN: String = "7"
    private val EIGHT: String = "8"
    private val NINE: String = "9"

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
            onDigitButtonClick("+")
        }
        btn_action_subtract.setOnClickListener {
            onDigitButtonClick("-")
        }
        btn_action_multiply.setOnClickListener {
            onDigitButtonClick("×")
        }
        btn_action_divide.setOnClickListener {
            onDigitButtonClick("÷")
        }
        btn_action_del.setOnClickListener {
            onDigitButtonClick("?")
        }
        btn_left_bracket.setOnClickListener {
            onDigitButtonClick("(")
        }
        btn_right_bracket.setOnClickListener {
            onDigitButtonClick(")")
        }
        btn_dot.setOnClickListener {
            onDigitButtonClick(".")
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
        val expression = tv_expression.text
        if (expression.isNullOrEmpty()) {
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

    private fun onBackspaceButtonClick() {
        val start = tv_expression.selectionStart
        val end = tv_expression.selectionEnd
        if (start == end) {
            val length = tv_expression.text.length
            if (length > 0) {
                tv_expression.text.delete(start - 1, start)
            }
        } else {
            tv_expression.text.replace(start, end, "")
        }
        evaluateExpression()
    }

    private fun evaluateExpression() {
        val expression = tv_expression.text
        if (expression.isNullOrEmpty()) {
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