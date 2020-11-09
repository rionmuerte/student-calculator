package space.prussak.calc.controller

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

enum class Operation(val sign : String) {
    ADDITION("+") {
        override fun calculate(a: String, b: String) : String {
            return num(a).add(num(b)).toString()
        }
    }, SUBTRACTION("-") {
        override fun calculate(a: String, b: String): String {
            return num(a).subtract(num(b)).toString()
        }
    }, DIVISION("÷") {
        override fun calculate(a: String, b: String): String {
            return num(a).divide(num(b), 100, RoundingMode.HALF_UP).toString()
        }
    }, MULTIPLICATION("×") {
        override fun calculate(a: String, b: String): String {
            return num(a).multiply(num(b)).toString()
        }
    }, NONE(" ") {
        override fun calculate(a: String, b: String): String {
            return ""
        }
    };

    abstract fun calculate(a: String, b: String) : String

    protected fun num(num: String) : BigDecimal {
        return BigDecimal(num, MathContext.UNLIMITED)
    }
}