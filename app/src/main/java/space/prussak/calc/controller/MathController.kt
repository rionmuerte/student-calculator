package space.prussak.calc.controller

class MathController {
    var operation = Operation.NONE

    fun calculate(num1: String, num2: String) : String {
        val currentOperation = operation
        operation = Operation.NONE
        return currentOperation.calculate(num1, num2)
    }

}