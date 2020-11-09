package space.prussak.calc.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

class TextFieldWatcher(field: TextView?, fontChangeService: FontChangeService?) : TextWatcher {
    private val field = field
    private val fontChanger = fontChangeService

    private var currentText: String? = null
    private var dontChange = false

    override fun afterTextChanged(s: Editable?) {
        if (dontChange) {
            dontChange = false
            field?.text = this.currentText
        }
        if (s?.length!! < currentText?.length!!) fontChanger?.increaseFonts()
        else fontChanger?.decreaseFonts()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (dontChange) return
        this.currentText = s.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (this.currentText?.contains('.')!! && s?.length != count) {
            val a = s?.subSequence(start, start + count)
            dontChange = a?.contains('.')!!
        }
    }
}