package space.prussak.calc.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

private val TRAILING_ZEROS_PATTERN = Regex("0+\$|\\.0+")

class TrailingZerosTrimer(textField: TextView) : TextWatcher {
    private val textField = textField
    override fun afterTextChanged(s: Editable?) {
        if (s?.contains('.')!! && s?.contains(TRAILING_ZEROS_PATTERN)) {
            textField.text = s?.replaceFirst(TRAILING_ZEROS_PATTERN, "")
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}