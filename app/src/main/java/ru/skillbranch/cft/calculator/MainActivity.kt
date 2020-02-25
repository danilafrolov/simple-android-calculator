package ru.skillbranch.cft.calculator

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

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
        btn_action_ternary.setOnClickListener {
            onDigitButtonClick("?")
        }
        btn_backspace.setOnClickListener {
            onBackspaceButtonClick()
        }
    }

    private fun onDigitButtonClick(number: String) {
        val start = tv_expression.selectionStart
        val end = tv_expression.selectionEnd
        tv_expression.text.replace(start, end, "")
        tv_expression.text.insert(start, number)
    }

    private fun onBackspaceButtonClick() {
        val start = tv_expression.selectionStart
        val end = tv_expression.selectionEnd
        if (start == end) {
            val length = tv_expression.text.length
            if (length > 0) {
                tv_expression.text.delete(start - 1, start)
            }
            return
        }
        tv_expression.text.replace(start, end, "")
    }
}