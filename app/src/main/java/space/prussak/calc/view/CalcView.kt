package space.prussak.calc.view

import android.content.Context
import android.content.res.TypedArray
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import space.prussak.calc.R
import space.prussak.calc.controller.MathController
import space.prussak.calc.controller.Operation

class CalcView(context: Context, controller: MathController) : LinearLayout(context) {

    private val controller : MathController

    private var lowerField : TextView? = null
    private var upperField : TextView? = null
    private var operationField : TextView? = null
    private var fontChangeService : FontChangeService? = null

    init {
        inflate(context, R.layout.main_layout, this)
        this.controller = controller
        initTextFields()
        initOperationsButtons()
        initNumpadButtons()
        initFunctionButtons()
    }

    private fun createFontSizesArray() : Array<Float> {
        val fontSizes = resources.obtainTypedArray(R.array.font_sizes)
        val size = fontSizes.length()
        val array = Array(size) { i -> fontSizes.getFloat(i, 32f)}
        fontSizes.recycle()
        return array
    }

    private fun initTextFields() {
        lowerField = findViewById(R.id.lower_number)
        upperField = findViewById(R.id.upper_number)
        operationField = findViewById(R.id.operation_sign)
        fontChangeService = FontChangeService(lowerField!!, upperField!!, createFontSizesArray())
        lowerField?.addTextChangedListener(TextFieldWatcher(lowerField, fontChangeService))
        upperField?.addTextChangedListener(TrailingZerosTrimer(upperField!!))
    }

    private fun initFunctionButtons() {
        val buttonEquals : Button = findViewById(R.id.button_equals)
        buttonEquals.setOnClickListener { executeOperation() }
        val backspaceButton : Button = findViewById(R.id.button_delete)
        backspaceButton.setOnClickListener {
            val len = lowerField?.text?.length!!
            lowerField?.text = lowerField?.text?.substring(0, len - 1)
            changeEmptyStringToZero()
        }
        val clearButton : Button = findViewById(R.id.button_clear)
        clearButton.setOnClickListener {
            upperField?.text = ""
            lowerField?.text = "0"
            pushOperation(Operation.NONE)
        }
    }

    private fun initOperationsButtons() {
        val buttonAdd: Button = findViewById(R.id.button_add)
        buttonAdd.setOnClickListener { pushOperation(Operation.ADDITION) }
        val buttonSubtract: Button = findViewById(R.id.button_subtract)
        buttonSubtract.setOnClickListener { pushOperation(Operation.SUBTRACTION) }
        val buttonDivide: Button = findViewById(R.id.button_divide)
        buttonDivide.setOnClickListener { pushOperation(Operation.DIVISION) }
        val buttonMultiply: Button = findViewById(R.id.button_multiply)
        buttonMultiply.setOnClickListener { pushOperation(Operation.MULTIPLICATION) }
    }

    private fun initNumpadButtons() {
        val buttons: TypedArray = resources.obtainTypedArray(R.array.numpad)
        for (i in 0 until 10) {
            val button: Button = findViewById(buttons.getResourceId(i, 0))
            button.setOnClickListener { numpadButtonOnClick("$i") }
        }
        val button000 : Button = findViewById(R.id.button_000)
        button000.setOnClickListener { numpadButtonOnClick("000") }
        val buttonDot : Button = findViewById(R.id.button_dot)
        buttonDot.setOnClickListener { numpadButtonOnClick(".") }

    }

    private fun numpadButtonOnClick(text : String) {
        lowerField?.append(text)
        if(lowerField?.text?.length!! > 1) {
            lowerField?.text = lowerField?.text?.replaceFirst(Regex("^0+"), "")
            changeEmptyStringToZero()
        }
    }

    private fun pushOperation(operation: Operation) {
        controller.operation = operation
        operationField?.text = controller.operation.sign
        executeOperation()
        fontChangeService?.increaseFonts()
        fontChangeService?.decreaseFonts()
    }

    private fun changeEmptyStringToZero() {
        if (lowerField?.text?.isEmpty()!!) {
            lowerField?.text = "0"
        }
    }

    private fun executeOperation() {
        if (controller.operation == Operation.NONE) return
        if (lowerField?.text?.isEmpty()!!) return
        if (upperField?.text?.isEmpty()!!) {
            moveNumberUp()
            return
        }
        var result = controller.calculate(upperField?.text?.toString()!!, lowerField?.text.toString())
        updateOperationResult(result)
    }

    private fun updateOperationResult(result: String) {
        upperField?.text = result
        lowerField?.text = ""
        operationField?.text = controller.operation.sign
    }

    private fun moveNumberUp() {
        upperField?.text = lowerField?.text
        lowerField?.text = ""
    }
}